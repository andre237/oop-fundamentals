package com.utility.config.factories;

import com.utility.service.contracts.TemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.List;

@Slf4j @Configuration
public class TemplateServiceFactory extends AbstractServiceFactory<TemplateService> {

    @Autowired
    public TemplateServiceFactory(@Value("${app.factory.impl.template}") String serviceIdentifier,
                                  List<TemplateService> registeredServices) {
        super(serviceIdentifier, registeredServices);
    }

    @Bean @Primary
    public TemplateService configureService() {
        return configureService(new TemplateService.NoOpTemplateService());
    }
}
