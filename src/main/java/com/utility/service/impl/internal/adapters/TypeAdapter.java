package com.utility.service.impl.internal.adapters;

public interface TypeAdapter<T> {

    String writeValue(T data);
    String writeValue(T data, String token);

}
