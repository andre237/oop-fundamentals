package com.utility.service.impl.internal.adapters;

import com.utility.util.TemplateWriter;

public interface TypeAdapter<T> {

    void proccessValue(T data, TemplateWriter template);
    void proccessValue(T data, String token, TemplateWriter template);

}
