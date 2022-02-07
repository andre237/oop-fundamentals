package com.utility.service.impl.internal.adapters;

import com.utility.service.impl.internal.adapters.bind.BasicInlineValueAdapter;
import com.utility.service.impl.internal.adapters.bind.CollectionValueAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TypeAdaptersHelper {

    private final Map<Class<?>, TypeAdapter> adaptersCache = new ConcurrentHashMap<>();

    @Autowired
    public TypeAdaptersHelper(BasicInlineValueAdapter basicInlineValueAdapter,
                              CollectionValueAdapter collectionValueAdapter) {
        adaptersCache.put(String.class, basicInlineValueAdapter);
        adaptersCache.put(Integer.class, basicInlineValueAdapter);
        adaptersCache.put(Long.class, basicInlineValueAdapter);
        adaptersCache.put(Double.class, basicInlineValueAdapter);
        adaptersCache.put(Boolean.class, basicInlineValueAdapter);

        adaptersCache.put(List.class, collectionValueAdapter);
    }

    public TypeAdapter getAdapterFor(Class<?> clazz) {
        if (Collection.class.isAssignableFrom(clazz)) {
            return adaptersCache.get(List.class);
        } else return adaptersCache.get(clazz);
    }

}
