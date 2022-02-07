package com.utility.service.contracts;

import com.utility.util.AppConstants;

public interface TemplateService extends FactoryCandidate {

    String getTemplate(String templateKey);
    String getSubTemplate(String subTemplateKey);

    class NoOpTemplateService implements TemplateService {
        @Override
        public String getImplIdentifier() {
            return AppConstants.NO_OP_IMPLEMENTATION;
        }

        @Override
        public String getTemplate(String templateKey) {
            return "<null>";
        }

        @Override
        public String getSubTemplate(String subTemplateKey) {
            return "<null>";
        }
    }

}
