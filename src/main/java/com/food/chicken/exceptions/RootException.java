package com.food.chicken.exceptions;

abstract public class RootException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private String message;
    private String errorCode;

    public RootException(String message) {
        this.message = message;
    }

    public RootException(String message, String errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }
}