package com.utility.service.impl.internal.adapters;

import com.utility.service.contracts.TemplateService;
import com.utility.service.impl.internal.adapters.bind.CollectionValueAdapterFactory;
import com.utility.service.impl.internal.adapters.bind.InlineValueAdapterFactory;
import com.utility.service.impl.internal.adapters.bind.MapObjectValueAdapterFactory;
import com.utility.service.impl.internal.adapters.bind.ReflectiveValueAdapterFactory;
import com.utility.util.TemplateWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Inspired on GSON approach to factory-build adapters, but instead
 * of reading/writing JSON, we are injecting JSON data into a text template
 * if placeholders for each property
 *
 * Each {@link TypeAdapter} implementation supports one or more Java types
 * and handles placeholders replacing differently
 */
@Service
public class TypeAdaptersContext {

    private final TemplateService templateService;
    private final Map<Class<?>, TypeAdapter<?>> adaptersCache;
    private final List<TypeAdapterFactory> factories;

    @Autowired
    public TypeAdaptersContext(TemplateService templateService) {
        this.templateService = templateService;
        this.adaptersCache = new ConcurrentHashMap<>();
        this.factories = Collections.unmodifiableList(this.addFactories());
    }

    private List<TypeAdapterFactory> addFactories() {
        List<TypeAdapterFactory> newFactories = new ArrayList<>();
        newFactories.add(new InlineValueAdapterFactory());
        newFactories.add(new CollectionValueAdapterFactory());
        newFactories.add(new MapObjectValueAdapterFactory());
        newFactories.add(new ReflectiveValueAdapterFactory());

        return newFactories;
    }

    public String proccessPlaceholders(String template, Object src) {
        return this.proccessPlaceholders(getAdapter(src.getClass()), new TemplateWriter(template), src);
    }

    @SuppressWarnings("unchecked")
    private <T> String proccessPlaceholders(TypeAdapter<?> adapter, TemplateWriter template, T src) {
        ((TypeAdapter<T>) adapter).proccessValue(src, template);
        return template.toString();
    }

    @SuppressWarnings("unchecked")
    public <T> TypeAdapter<T> getAdapter(Class<T> clazz) {
        TypeAdapter<?> adapter = this.adaptersCache.get(clazz);
        if (adapter != null) {
            return (TypeAdapter<T>) adapter;
        } else {
            TypeAdapter<T> candidate = null;
            var iterator = factories.iterator();

            while (candidate == null) {
                if (!iterator.hasNext()) throw new IllegalArgumentException("Unsupported type [" + clazz.getSimpleName() + "]");
                candidate = iterator.next().create(this, clazz);
            }

            this.adaptersCache.put(clazz, candidate);
            return candidate;
        }
    }

    public TemplateService getTemplateService() {
        return templateService;
    }
}
