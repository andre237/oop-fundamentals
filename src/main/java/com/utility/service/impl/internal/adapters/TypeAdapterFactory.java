package com.utility.service.impl.internal.adapters;

public interface TypeAdapterFactory {

    <T> TypeAdapter<T> create(TypeAdaptersContext context, Class<T> clazz);

}
