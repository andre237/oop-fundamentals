package com.utility.service.impl.internal.adapters.bind;

import com.utility.service.contracts.PlaceholderResolverService;
import com.utility.service.impl.internal.adapters.TypeAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class CollectionValueAdapter implements TypeAdapter {

    private final PlaceholderResolverService resolverService;

    @Autowired
    public CollectionValueAdapter(@Lazy PlaceholderResolverService resolverService) {
        this.resolverService = resolverService;
    }

    @Override
    public String writeValue(Object data) {
        return "[null]";
    }

    @Override
    public String writeValue(Object data, String composedPlaceholder) {
        if (!Collection.class.isAssignableFrom(data.getClass())) return "";
        StringBuilder builder = new StringBuilder();

        for (Object item : ((Collection) data)) {
            builder.append(resolverService.resolve(composedPlaceholder, item));
        }

        return builder.toString();
    }
}
