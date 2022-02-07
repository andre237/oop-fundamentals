package com.utility.api;

import com.utility.common.dto.RequestErrorDTO;
import com.utility.common.exceptions.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@ControllerAdvice(basePackages = {"com.utility.api"})
public class ControllerExceptionsHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<RequestErrorDTO> handleBusinessException(BusinessException ex) {
        return ResponseEntity.badRequest().body(
                RequestErrorDTO.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .detail(ex.getMessage()).build()
        );
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RequestErrorDTO> handleConstraintViolationException(MethodArgumentNotValidException ex) {
        return ResponseEntity.unprocessableEntity().body(
                createResponseFromBeanValidation(ex.getBindingResult())
        );
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(BindException.class)
    public ResponseEntity<RequestErrorDTO> handleConstraintViolationException(BindException ex) {
        return ResponseEntity.unprocessableEntity().body(
                createResponseFromBeanValidation(ex.getBindingResult())
        );
    }

    private RequestErrorDTO createResponseFromBeanValidation(BindingResult bindingResult) {
        List<RequestErrorDTO.FieldError> validations = bindingResult.getAllErrors().stream()
                .map(error -> error instanceof FieldError ?
                        RequestErrorDTO.newFieldError(((FieldError) error).getField(), error.getDefaultMessage()) :
                        RequestErrorDTO.newFieldError(error.getDefaultMessage()))
                .collect(Collectors.toList());

        return RequestErrorDTO.builder()
                .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .timestamp(new Date())
                .detail("Validation error")
                .fields(validations)
                .build();
    }

}
