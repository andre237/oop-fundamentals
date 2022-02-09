package com.utility.service.impl;

import com.utility.common.constants.EmailType;
import com.utility.service.contracts.PlaceholderResolverService;
import com.utility.service.contracts.TemplateService;
import com.utility.service.impl.internal.adapters.TypeAdaptersContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TemplatedPlaceholderResolverService implements PlaceholderResolverService {

    private final TemplateService templateService;
    private final TypeAdaptersContext typeAdaptersContext;

    @Override
    public String getImplIdentifier() {
        return "template";
    }

    @Override
    public String resolve(EmailType type, Object data) {
        String template = templateService.getTemplate(type.getTemplateName());
        return typeAdaptersContext.proccessPlaceholders(template, data);
    }

}
