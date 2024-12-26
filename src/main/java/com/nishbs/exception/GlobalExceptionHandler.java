package com.nishbs.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    private List<String> addMapValue(List<String> list, String newValue) {
        list.add(newValue);
        return list;
    }

    @ExceptionHandler(value = { CustomerNotFoundException.class })
    public ResponseEntity<ApiError<String>> handleCustomerNotFoundException(CustomerNotFoundException ex) {
        ApiError<String> apiError = new ApiError<>();
        apiError.setId(UUID.randomUUID().toString());
        apiError.setErrorTime(new Date());
        apiError.setErrors(ex.getMessage());

        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {FileNotFoundException.class})
    public ResponseEntity<ApiError<String>> handleFileNotFoundException(FileNotFoundException ex) {
        ApiError<String> apiError = new ApiError<>();
        apiError.setId(UUID.randomUUID().toString());
        apiError.setErrorTime(new Date());
        apiError.setErrors(ex.getMessage());

        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {RefreshTokenException.class})
    public ResponseEntity<ApiError<String>> handleRefreshTokenException(RefreshTokenException ex) {
        ApiError<String> apiError = new ApiError<>();
        apiError.setId(UUID.randomUUID().toString());
        apiError.setErrorTime(new Date());
        apiError.setErrors(ex.getMessage());

        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {AuthenticationException.class})
    public ResponseEntity<ApiError<String>> handleAuthenticationException(AuthenticationException ex) {
        ApiError<String> apiError = new ApiError<>();
        apiError.setId(UUID.randomUUID().toString());
        apiError.setErrorTime(new Date());
        apiError.setErrors(ex.getMessage());

        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = { MethodArgumentNotValidException.class })
    public ResponseEntity<ApiError> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, List<String>> errorsMap = new HashMap<>();
        for (ObjectError objError : ex.getBindingResult().getAllErrors()) {
            String fieldName = ((FieldError)objError).getField();
            if (errorsMap.containsKey(fieldName)) {
                errorsMap.put(fieldName, addMapValue(errorsMap.get(fieldName), objError.getDefaultMessage()));
            }
            else {
                errorsMap.put(fieldName, addMapValue(new ArrayList<>(), objError.getDefaultMessage()));
            }
        }

        return ResponseEntity.badRequest().body(createApiError(errorsMap));
    }

    private <T> ApiError<T> createApiError(T errors) {
        ApiError<T> apiError = new ApiError<T>();
        apiError.setId(UUID.randomUUID().toString());
        apiError.setErrorTime(new Date());
        apiError.setErrors(errors);

        return apiError;
     }
}
