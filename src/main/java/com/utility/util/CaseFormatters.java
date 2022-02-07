package com.utility.util;

import com.google.common.base.CaseFormat;

public class CaseFormatters {

    public static String camelToSnakeCase(String camel) {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, camel);
    }

}
