package com.example.currency.exception;

/**
 * Created by sergey on 15.10.2018.
 */
public class WebClientException extends Exception {

    public WebClientException(String msg) {
        super(msg);
    }

    public WebClientException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
