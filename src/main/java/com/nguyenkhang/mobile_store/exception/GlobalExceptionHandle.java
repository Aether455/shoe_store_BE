package com.nguyenkhang.mobile_store.exception;


import com.nguyenkhang.mobile_store.dto.ApiResponse;
import jakarta.validation.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;
import java.util.Objects;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandle {

    private static final String MIN_ATTRIBUTE = "min";


    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse> handlingException(Exception exception) {
        log.error("Exception: ", exception);

        ApiResponse apiResponse = new ApiResponse<>();

        ErrorCode errorCode = ErrorCode.UNCATEGORIZED_EXCEPTION;

        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());
        return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);
    }


    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handlingAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());

        return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);
    }


    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse> handlingValidation(MethodArgumentNotValidException exception) {


        String enumKey = exception.getFieldError().getDefaultMessage();

        Map<String, Object> attributes = null;
        ErrorCode errorCode;
        try {

            errorCode = ErrorCode.valueOf(enumKey);

            ConstraintViolation constraintViolation = exception.getBindingResult().getFieldErrors().getFirst().unwrap(ConstraintViolation.class);

            attributes = constraintViolation.getConstraintDescriptor().getAttributes();
            log.info(attributes.toString());
        } catch (IllegalArgumentException ignored) {
            errorCode = ErrorCode.INVALID_KEY;

        }
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(Objects.nonNull(attributes) ? mapAttribute(errorCode.getMessage(), attributes) : errorCode.getMessage());

        return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);
    }


    private String mapAttribute(String message, Map<String, Object> attributes) {
        String minValue = String.valueOf(attributes.get(MIN_ATTRIBUTE));

        return message.replace("{" + MIN_ATTRIBUTE + "}", minValue);
    }
}
