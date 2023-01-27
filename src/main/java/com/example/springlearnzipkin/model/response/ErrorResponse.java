package com.example.springlearnzipkin.model.response;

public class ErrorResponse extends Response{

    public ErrorResponse(String message) {
        super(false, message);
    }
}
