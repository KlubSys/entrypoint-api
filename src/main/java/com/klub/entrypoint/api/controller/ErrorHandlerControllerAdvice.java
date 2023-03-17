package com.klub.entrypoint.api.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JacksonException;
import com.klub.entrypoint.api.exception.*;
import com.klub.entrypoint.api.helper.utils.CamelCaseUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ErrorHandlerControllerAdvice {

    //Exception
    @ExceptionHandler(ErrorOccurredException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public void handleErrorOccurredException(ErrorOccurredException e){
        System.err.println(e.getMessage());
        e.printStackTrace();
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleNotFoundException(NotFoundException e){
        System.err.println(e.getMessage());
        e.printStackTrace();
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public void handleForbidden(ForbiddenException e){
        System.err.println(e.getMessage());
        e.printStackTrace();
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public void handleUnauthorizedException(UnauthorizedException e){
        System.err.println(e.getMessage());
        e.printStackTrace();
    }

    @ExceptionHandler(BadRequestContentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleBadContentException(BadRequestContentException e){
        System.err.println(e.getMessage());
        e.printStackTrace();
    }

    @ExceptionHandler(JacksonException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public void handleJacksonException(JacksonException e){
        System.err.println(e.getMessage());
        e.printStackTrace();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ValidationErrorResponse onConstraintValidationException(
            ConstraintViolationException e) {
        ValidationErrorResponse error = new ValidationErrorResponse();
        for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
            error.getViolations().add(
                    new Violation(
                            CamelCaseUtils.toSnakeCase(violation.getPropertyPath().toString()),
                            violation.getMessage()
                    )
            );
        }
        return error;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ValidationErrorResponse onMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        ValidationErrorResponse error = new ValidationErrorResponse();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            error.getViolations().add(
                    new Violation(
                            CamelCaseUtils.toSnakeCase(fieldError.getField()),
                            fieldError.getDefaultMessage()
                    )
            );
        }
        return error;
    }


    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class ValidationErrorResponse {
        @JsonProperty("errors")
        private List<Violation> violations = new ArrayList<>();
    }

    @AllArgsConstructor
    @Data
    public static class Violation {
        @JsonProperty("field")
        private final String fieldName;
        private final String message;
    }
}
