package com.utility.config.factories;

import com.utility.service.contracts.FactoryCandidate;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * OpenClosed (SOLID) principle translated to Sprint IOC
 *
 * Whenever we need to change the way of doing something (whatever it is, this class is abstract hehe)
 * we only need to create a new concrete class that implements this new way
 * Previous classes are CLOSED to be modified but we are OPEN to extend behaviours
 *
 * With a little hand from Spring, we can inject a List of implementations for a given interface
 * and choose based on a string identifier. Hence, this class will never know anything about the
 * implementations.
 *
 * @param <T> an interface or class which we want to configure the desired implementation
 */
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
