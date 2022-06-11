package com.example.rsoi_course_work.rental_service.exception;

public class ValidationErrorResponse extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ValidationErrorResponse(String msg) {
        super(msg);
    }
}