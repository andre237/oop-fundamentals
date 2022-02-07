package com.utility.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data @Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestErrorDTO {

    private Integer status;
    private Date timestamp = new Date();
    private String detail;
    private List<FieldError> fields;

    public static FieldError newFieldError(String name, String detail) {
        return new FieldError(name, detail);
    }

    public static FieldError newFieldError(String detail) {
        return newFieldError(null, detail);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FieldError {
        private String fieldName;
        private String errorDetail;
    }

}
