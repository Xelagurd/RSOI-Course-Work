package com.example.rsoi_course_work.front;

import com.example.rsoi_course_work.front.exception.ErrorMessage;
import com.example.rsoi_course_work.front.exception.FeignRetriesException;
import com.example.rsoi_course_work.front.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class FrontControllerExceptionHandler {
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage validationException(ValidationException ex, WebRequest request) {
        return new ErrorMessage(HttpStatus.BAD_REQUEST.value(), new Date(), ex.getMessage(),
                request.getDescription(true));
    }

    @ExceptionHandler(FeignRetriesException.class)
    @ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
    public ErrorMessage feignExceptionHandler(Exception ex, WebRequest request) {
        return new ErrorMessage(HttpStatus.SERVICE_UNAVAILABLE.value(), new Date(), ex.getMessage(),
                request.getDescription(false));
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage globalExceptionHandler(Exception ex, WebRequest request) {
        return new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), new Date(), ex.getMessage(),
                request.getDescription(false));
    }
}
