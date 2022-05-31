package com.example.rest.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler
{
    @ExceptionHandler(ExceptionMsg.class)
    public final ResponseEntity<ErrorResponse> handleException
            (ExceptionMsg ex)
    {
        ErrorResponse error = new ErrorResponse(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }



}


