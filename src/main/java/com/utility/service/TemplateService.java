package com.utility.service;

import com.utility.util.AppConstants;

public interface TemplateService extends FactoryCandidate {

    String getTemplate(String templateKey);

    class NoOpTemplateService implements TemplateService {
        @Override
        public String getImplIdentifier() {
            return AppConstants.NO_OP_IMPLEMENTATION;
        }

        @Override
        public String getTemplate(String templateKey) {
            return "<null>";
        }
    }

}
