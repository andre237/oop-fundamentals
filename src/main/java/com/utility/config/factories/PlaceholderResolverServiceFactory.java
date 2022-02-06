package com.utility.config.factories;

import com.utility.service.PlaceholderResolverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.List;

@Configuration
public class PlaceholderResolverServiceFactory extends AbstractServiceFactory<PlaceholderResolverService> {

    @Autowired
    public PlaceholderResolverServiceFactory(
                                @Value("${app.factory.impl.placehoders}") String serviceIdentifier,
                                List<PlaceholderResolverService> registeredServices) {
        super(serviceIdentifier, registeredServices);
    }

    @Bean @Primary
    public PlaceholderResolverService placeholderResolverService() {
        return configureService(new PlaceholderResolverService.NoOpPlaceholderResolverService());
    }
}
