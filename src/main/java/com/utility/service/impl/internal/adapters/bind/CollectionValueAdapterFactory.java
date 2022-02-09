package com.utility.service.impl.internal.adapters.bind;

import com.utility.service.impl.internal.adapters.TypeAdapter;
import com.utility.service.impl.internal.adapters.TypeAdapterFactory;
import com.utility.service.impl.internal.adapters.TypeAdaptersContext;
import com.utility.util.CaseFormatters;
import com.utility.util.TemplateWriter;
import java.util.Collection;

public class CollectionValueAdapterFactory implements TypeAdapterFactory {

    @Override
    public <T> TypeAdapter<T> create(TypeAdaptersContext context, Class<T> clazz) {
        return !Collection.class.isAssignableFrom(clazz) ? null : (TypeAdapter<T>) new Adapter<>(context);
    }

    private static final class Adapter<T> implements TypeAdapter<Collection<T>> {

        private static final String PLACEHOLDER_WITH_TEMPLATE = "$[%s]";
        private final TypeAdaptersContext context;

        public Adapter(TypeAdaptersContext context) {
            this.context = context;
        }

        @Override
        public void proccessValue(Collection<T> data, TemplateWriter template) {

        }

        @Override
        public void proccessValue(Collection<T> data, String token, TemplateWriter template) {
            String composedPlaceholder = String.format(PLACEHOLDER_WITH_TEMPLATE, token);
            if (template.contains(composedPlaceholder)) {
                String subTemplate = context.getTemplateService().getSubTemplate(CaseFormatters.camelToSnakeCase(token));
                StringBuilder listBuilder = new StringBuilder();

                for (T t : data) {
                    TemplateWriter subWriter = new TemplateWriter(subTemplate);
                    TypeAdapter<T> adapter = (TypeAdapter<T>) context.getAdapter(t.getClass());
                    adapter.proccessValue(t, subWriter);

                    listBuilder.append(subWriter);
                }

                template.replace(composedPlaceholder, listBuilder.toString());
            }
        }
    }

}
