package com.utility.common.constants;

import java.util.Arrays;
import java.util.List;

public enum EmailType {

    NEW_PRODUCT_MAIL("product_1"),
    PURCHASE_ORDER_MAIL("new_order_1", "order_items");

    private final String templateName;
    private final List<String> subTemplates;

    EmailType(String template, String... subtemplates) {
        this.templateName = template;
        this.subTemplates = Arrays.asList(subtemplates);
    }

    public String getTemplateName() {
        return templateName;
    }

    public List<String> getSubTemplates() {
        return subTemplates;
    }
}
