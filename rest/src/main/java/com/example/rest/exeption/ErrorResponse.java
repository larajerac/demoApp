package com.example.rest.exeption;

public class ErrorResponse
{
    public ErrorResponse(String message) {
        super();
        this.message = message;
    }

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}