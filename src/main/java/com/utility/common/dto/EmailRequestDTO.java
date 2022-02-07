package com.utility.common.dto;

import com.utility.common.constants.EmailType;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class EmailRequestDTO {

    @NotNull
    private EmailType type;

    @NotNull @Min(value = 1)
    private List<String> recipients;

    private Integer retry;

    private Object data;

}
