
package com.task.CustomerManagementApplication.controller;

import lombok.extern.slf4j.Slf4j;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.task.CustomerManagementApplication.dto.ApiErrorResponse;

@RestController
@ControllerAdvice
@Slf4j
public class ErrorController {

    @ExceptionHandler(Exception.class)
    private ResponseEntity<ApiErrorResponse> globalException(Exception ex){
            log.error("Error Happend",ex);
            ApiErrorResponse apiErrorResponse=ApiErrorResponse.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("An UnExcepected Error Occured")
                    .build();

        return new  ResponseEntity<>(apiErrorResponse,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    private ResponseEntity<ApiErrorResponse> illegalArgumentExceptionHandling(IllegalArgumentException ex){
        ApiErrorResponse apiErrorResponse=ApiErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .build();

        return new  ResponseEntity<>(apiErrorResponse,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalStateException.class)
    private ResponseEntity<ApiErrorResponse> illegalStateExceptionHandling(IllegalStateException ex){
        ApiErrorResponse apiErrorResponse=ApiErrorResponse.builder()
                .status(HttpStatus.CONFLICT.value())
                .message(ex.getMessage())
                .build();

        return new  ResponseEntity<>(apiErrorResponse,HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BadCredentialsException.class)
    private ResponseEntity<ApiErrorResponse> illegalStateExceptionHandling(BadCredentialsException ex){
        ApiErrorResponse apiErrorResponse=ApiErrorResponse.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .message("incorrect username or password")
                .build();   

        return new  ResponseEntity<>(apiErrorResponse,HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFound(NoSuchElementException ex){

    ApiErrorResponse response = ApiErrorResponse.builder()
            .status(HttpStatus.NOT_FOUND.value())
            .message(ex.getMessage())
            .build();

    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }   
}
