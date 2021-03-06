package com.example.rsoi_course_work.station_service;

import com.example.rsoi_course_work.station_service.exception.ErrorMessage;
import com.example.rsoi_course_work.station_service.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class StationControllerExceptionHandler {
    @ExceptionHandler(ErrorResponse.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage errorResponse(ErrorResponse ex, WebRequest request) {
        return new ErrorMessage(HttpStatus.NOT_FOUND.value(), new Date(), ex.getMessage(),
                request.getDescription(true));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage globalExceptionHandler(Exception ex, WebRequest request) {
        return new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), new Date(), ex.getMessage(),
                request.getDescription(true));
    }
}
