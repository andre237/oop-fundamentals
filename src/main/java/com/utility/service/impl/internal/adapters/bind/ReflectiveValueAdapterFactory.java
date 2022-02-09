package com.utility.service.impl.internal.adapters.bind;

import com.utility.service.impl.internal.adapters.TypeAdapter;
import com.utility.service.impl.internal.adapters.TypeAdapterFactory;
import com.utility.service.impl.internal.adapters.TypeAdaptersContext;
import com.utility.util.TemplateWriter;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ReflectiveValueAdapterFactory implements TypeAdapterFactory {

    @Override
    public <T> TypeAdapter<T> create(TypeAdaptersContext context, Class<T> clazz) {
        return !Object.class.isAssignableFrom(clazz) ? null : new Adapter<>(context);
    }

    private static final class Adapter<T> implements TypeAdapter<T> {

        private final TypeAdaptersContext context;

        public Adapter(TypeAdaptersContext context) {
            this.context = context;
        }

        @Override
        public void proccessValue(Object data, TemplateWriter template) {
            var propertyDescriptors = BeanUtils.getPropertyDescriptors(data.getClass());
            for (var pd : propertyDescriptors) {
                Method readMethod = pd.getReadMethod();
                if (!Modifier.isPublic(readMethod.getModifiers())) {
                    readMethod.setAccessible(true);
                }

                try {
                    String placeholderName = pd.getName();
                    Object placeHolderValue = readMethod.invoke(data);

                    TypeAdapter<Object> adapter = (TypeAdapter<Object>) context.getAdapter(placeHolderValue.getClass());
                    adapter.proccessValue(placeHolderValue, placeholderName, template);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void proccessValue(Object data, String token, TemplateWriter template) {

        }
    }
}
