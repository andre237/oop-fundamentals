package com.utility.service.impl.internal.adapters.bind;

import com.utility.service.impl.internal.adapters.TypeAdapter;
import com.utility.service.impl.internal.adapters.TypeAdapterFactory;
import com.utility.service.impl.internal.adapters.TypeAdaptersContext;
import com.utility.util.TemplateWriter;

import java.util.Map;

public class MapObjectValueAdapterFactory implements TypeAdapterFactory {

    @Override
    public <T> TypeAdapter<T> create(TypeAdaptersContext context, Class<T> clazz) {
        return !Map.class.isAssignableFrom(clazz) ? null : (TypeAdapter<T>) new Adapter<>(context);
    }

    private static final class Adapter<V> implements TypeAdapter<Map<String, V>> {

        private final TypeAdaptersContext context;

        public Adapter(TypeAdaptersContext context) {
            this.context = context;
        }

        @Override
        public void proccessValue(Map<String, V> data, TemplateWriter template) {
            for (var entry : data.entrySet()) {
                String placeholderName = entry.getKey();
                V placeHolderValue = entry.getValue();

                TypeAdapter<V> adapter = (TypeAdapter<V>) context.getAdapter(placeHolderValue.getClass());
                adapter.proccessValue(placeHolderValue, placeholderName, template);
            }
        }

        @Override
        public void proccessValue(Map<String, V> data, String token, TemplateWriter template) {

        }
    }

}
