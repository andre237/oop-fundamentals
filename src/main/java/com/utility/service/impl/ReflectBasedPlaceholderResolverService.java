package com.utility.service.impl;

import com.utility.common.constants.EmailType;
import com.utility.service.contracts.PlaceholderResolverService;
import com.utility.service.contracts.TemplateService;
import com.utility.service.impl.internal.adapters.TypeAdapter;
import com.utility.service.impl.internal.adapters.TypeAdaptersHelper;
import com.utility.util.CaseFormatters;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import static java.lang.reflect.Modifier.isPublic;

@Service
public class ReflectBasedPlaceholderResolverService implements PlaceholderResolverService {

    // use this when the object has list attribute that requires an individual template for each item
    private static final String LIST_PLACEHOLDER = "$[%s]";
    private static final String BASIC_PLACEHOLDER = "${%s}";

    private final TemplateService templateService;
    private final TypeAdaptersHelper typeAdaptersHelper;

    @Autowired
    public ReflectBasedPlaceholderResolverService(TemplateService templateService,
                                                  TypeAdaptersHelper typeAdaptersHelper) {
        this.templateService = templateService;
        this.typeAdaptersHelper = typeAdaptersHelper;
    }

    @Override
    public String getImplIdentifier() {
        return "reflection";
    }

    @Override
    public String resolve(EmailType type, Object data) {
        String template = templateService.getTemplate(type.getTemplateName());
        return this.resolve(template, data);
    }

    @Override
    public String resolve(String template, Object data) {
        if (!Map.class.isAssignableFrom(data.getClass())) return null;

        Map<String, ?> keyAndValue = (Map<String, ?>) data;
        for (var entry : keyAndValue.entrySet()) {

            String phName = entry.getKey();
            Object phValue = entry.getValue();

            try {
                String replaceRegex;
                String replaceContent;
                boolean containsListPlaceholder = template.contains(String.format(LIST_PLACEHOLDER, phName));
                if (containsListPlaceholder) {
                    replaceRegex = String.format(LIST_PLACEHOLDER, phName);

                    var subTemplateName = CaseFormatters.camelToSnakeCase(phName);
                    replaceContent = this.getReplacedValue(
                            phValue,
                            templateService.getSubTemplate(subTemplateName)
                    );
                } else {
                    replaceRegex = String.format(BASIC_PLACEHOLDER, phName);
                    replaceContent = this.getReplacedValue(phValue);
                }

                template = template.replace(replaceRegex, replaceContent);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return template;
    }

    private <T> String getReplacedValue(T value) {
        TypeAdapter adapter = typeAdaptersHelper.getAdapterFor(value.getClass());
        return adapter.writeValue(value);
    }

    private <T> String getReplacedValue(T value, String composedPlaceholder) {
        TypeAdapter adapter = typeAdaptersHelper.getAdapterFor(value.getClass());
        return adapter.writeValue(value, composedPlaceholder);
    }
}
