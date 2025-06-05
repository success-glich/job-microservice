package com.jobms.auth_server.auth.exception;


import com.jobms.auth_server.auth.utils.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFound(ResourceNotFoundException ex){
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    public  ResponseEntity<Object> handleBadRequest(BadRequestException ex){
        return buildResponse(HttpStatus.BAD_REQUEST,ex.getMessage());
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public  ResponseEntity<Object> handleDuplicateResource(DuplicateResourceException ex){
        return  buildResponse(HttpStatus.CONFLICT,ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public  ResponseEntity<Object> handleGeneralError(Exception ex){
        log.error("Something went wrong: "+ex.getMessage());
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR,"Something went wrong");
    }

    private ResponseEntity<Object> buildResponse(HttpStatus status, String message) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status",status.value());
        body.put("error", status.getReasonPhrase());

        return  ResponseEntity.status(status).body(new ApiResponse<>(false,message,body));
    }
}
