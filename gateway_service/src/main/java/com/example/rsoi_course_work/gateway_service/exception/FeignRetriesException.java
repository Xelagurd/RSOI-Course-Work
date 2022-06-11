package com.example.rsoi_course_work.gateway_service.exception;

public class FeignRetriesException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public FeignRetriesException(String msg) {
        super(msg);
    }
}