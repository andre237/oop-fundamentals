package com.utility.config.factories;

import com.utility.service.FactoryCandidate;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public abstract class AbstractServiceFactory<T extends FactoryCandidate> {

    private final String serviceIdentifier;
    private final Map<String, T> serviceCache;

    public AbstractServiceFactory(String serviceIdentifier,
                                  List<T> registeredServices) {
        this.serviceIdentifier = serviceIdentifier;
        this.serviceCache = registeredServices.stream()
                .collect(Collectors.toMap(FactoryCandidate::getImplIdentifier, Function.identity()));
    }

    public T configureService() {
        return configureService(null);
    }

    public T configureService(T defaultImpl) {
        final var service = serviceCache.getOrDefault(serviceIdentifier, defaultImpl);
        log.info("Configured {} implementation", service.getClass().getSimpleName());
        return service;
    }

}
