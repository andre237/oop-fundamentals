package com.utility.service.impl.internal.adapters.bind;

import com.utility.service.impl.internal.adapters.TypeAdapter;
import org.springframework.stereotype.Component;

@Component
public class BasicInlineValueAdapter implements TypeAdapter {

    @Override
    public String writeValue(Object data) {
        return data.toString();
    }

    @Override
    public String writeValue(Object data, String composedPlaceholder) {
        return "null";
    }
}
