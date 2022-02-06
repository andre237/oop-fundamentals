package com.utility.service.impl;

import com.utility.service.PlaceholderResolverService;
import org.springframework.stereotype.Service;

@Service
public class ReflectBasedPlaceholderResolverService implements PlaceholderResolverService {

    @Override
    public String getImplIdentifier() {
        return "reflection";
    }

    @Override
    public String resolve(String template, Object data) {
        return null;
    }
}
