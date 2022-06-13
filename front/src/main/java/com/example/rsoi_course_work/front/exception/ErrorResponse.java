package com.example.rsoi_course_work.front.exception;

public class ErrorResponse extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ErrorResponse(String msg) {
        super(msg);
    }
}
