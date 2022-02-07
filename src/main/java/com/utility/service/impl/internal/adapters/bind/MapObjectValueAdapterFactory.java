package com.utility.service.impl.internal.adapters.bind;

import com.google.common.reflect.Reflection;
import com.utility.service.impl.internal.adapters.TypeAdapter;
import com.utility.service.impl.internal.adapters.TypeAdapterFactory;
import com.utility.service.impl.internal.adapters.TypeAdaptersContext;
import com.utility.util.GsonReflectionUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Type;
import java.util.Map;

public class MapObjectValueAdapterFactory implements TypeAdapterFactory {

    @Override
    public <T> TypeAdapter<T> create(TypeAdaptersContext context, Class<T> clazz) {
        if (!Map.class.isAssignableFrom(clazz)) {
            return null;
        } else {
            Class<?> rawType = GsonReflectionUtils.getRawType(clazz);
            Type mapValueType = GsonReflectionUtils.getMapKeyAndValueTypes(clazz, rawType)[1];
            var valueAdapter = context.getAdapter(mapValueType.getClass());

            return new MapObjectValueAdapterFactory.Adapter();
        }
    }

    private final class Adapter<V> implements TypeAdapter<Map<String, V>> {
        @Override
        public String writeValue(Map<String, V> data) {
            return null;
        }

        @Override
        public String writeValue(Map<String, V> data, String token) {
            return null;
        }
    }

}
