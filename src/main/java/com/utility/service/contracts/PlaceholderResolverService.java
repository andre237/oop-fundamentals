package com.utility.service.contracts;

import com.utility.util.AppConstants;

public interface PlaceholderResolverService extends FactoryCandidate {

    String resolve(String template, Object data);

    class NoOpPlaceholderResolverService implements PlaceholderResolverService {
        @Override
        public String getImplIdentifier() {
            return AppConstants.NO_OP_IMPLEMENTATION;
        }

        @Override
        public String resolve(String template, Object data) {
            return template; // i'm dummy, i cant resolve it
        }
    }

}
