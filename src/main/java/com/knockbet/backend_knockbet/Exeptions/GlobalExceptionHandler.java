package com.knockbet.backend_knockbet.Exeptions;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationException(MethodArgumentNotValidException ex){

        Map<String, String> errorMapper = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {

            String message = error.getDefaultMessage();

            if (error instanceof FieldError fieldError) {
                errorMapper.put(fieldError.getField(), message);
            } else {
                errorMapper.put("_global", message);
            }

        });

        return errorMapper;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public Map<String, String> handleConstraintViolation(ConstraintViolationException ex) {
        Map<String, String> errorMapper = new HashMap<>();

        ex.getConstraintViolations().forEach(v -> {
            String path = (v.getPropertyPath() == null) ? "_global" : v.getPropertyPath().toString();
            errorMapper.put(path, v.getMessage());
        });

        return errorMapper;
    }
}
