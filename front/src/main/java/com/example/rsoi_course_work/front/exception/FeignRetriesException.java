package com.example.rsoi_course_work.front.exception;

public class FeignRetriesException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public FeignRetriesException(String msg) {
        super(msg);
    }
}