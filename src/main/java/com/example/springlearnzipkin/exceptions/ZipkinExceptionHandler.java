package com.example.springlearnzipkin.exceptions;


import brave.Span;
import brave.Tracer;
import com.example.springlearnzipkin.model.response.ErrorResponse;
import com.example.springlearnzipkin.model.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
/*import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;*/
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ZipkinExceptionHandler {

    private final Tracer tracer;

    @Autowired
    public ZipkinExceptionHandler(Tracer tracer) {
        this.tracer = tracer;
    }


    @ExceptionHandler(value = { ItemNotFoundException.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response handleItemNotFoundException(Exception ex) {
        generateSpan("handleItemNotFoundExceptions",ex);
        return new ErrorResponse(ex.getMessage());
    }

    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }

    private void generateSpan(String operationName, Exception ex){
        Span span = tracer.nextSpan().name(operationName).start();
        try (Tracer.SpanInScope ws = tracer.withSpanInScope(span)) {
            Map<String,Object> logs = new HashMap<>();
            logs.put("message", ex.getMessage());
            logs.put("errorType", ex.getClass());
            span.tag("error",logs.get("message").toString());
            // do some work
        } finally {
            span.finish();
        }
    }

}
