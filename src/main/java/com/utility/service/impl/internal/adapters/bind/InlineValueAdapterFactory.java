package com.utility.service.impl.internal.adapters.bind;

import com.utility.service.impl.internal.adapters.TypeAdapter;
import com.utility.service.impl.internal.adapters.TypeAdapterFactory;
import com.utility.service.impl.internal.adapters.TypeAdaptersContext;
import com.utility.util.TemplateWriter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class InlineValueAdapterFactory implements TypeAdapterFactory {

    private final List<Class<?>> supportedTypes;

    public InlineValueAdapterFactory() {
        supportedTypes = List.of(
                String.class,
                Integer.class,
                Long.class,
                Double.class,
                BigDecimal.class,
                Date.class
        );
    }

    @Override
    public <T> TypeAdapter<T> create(TypeAdaptersContext context, Class<T> clazz) {
        return supportedTypes.contains(clazz) ? new Adapter<>() : null;
    }

    private static final class Adapter<T> implements TypeAdapter<T> {

        private static final String BASIC_PLACEHOLDER = "${%s}";

        @Override
        public void proccessValue(T data, TemplateWriter template) {
            this.proccessValue(data, "", template);
        }

        @Override
        public void proccessValue(T data, String token, TemplateWriter template) {
            template.replace(String.format(BASIC_PLACEHOLDER, token), data.toString());
        }
    }
}
