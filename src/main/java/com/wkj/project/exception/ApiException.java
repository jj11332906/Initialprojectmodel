package com.wkj.project.exception;

/**
 * Created by xun on 2017/12/19.
 */
public class ApiException extends RuntimeException {

    private String message;

    public ApiException() {
        super();
    }

    public ApiException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
