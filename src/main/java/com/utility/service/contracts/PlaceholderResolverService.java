package com.utility.service.contracts;

import com.utility.common.constants.EmailType;
import com.utility.util.AppConstants;

public interface PlaceholderResolverService extends FactoryCandidate {

    String resolve(EmailType type, Object data);
    String resolve(String template, Object data);

    class NoOpPlaceholderResolverService implements PlaceholderResolverService {
        @Override
        public String getImplIdentifier() {
            return AppConstants.NO_OP_IMPLEMENTATION;
        }

        @Override
        public String resolve(EmailType type, Object data) {
            return "<null>";
        }

        @Override
        public String resolve(String template, Object data) {
            return "<null>";
        }
    }

}
