package com.reply.videostreaming.exception;

/**
 *
 * @author sraamasubbu
 */
public class UserUnderAgeException extends RuntimeException {

    public UserUnderAgeException() {
    }

    public UserUnderAgeException(String message) {
        super(message);
    }

    public UserUnderAgeException(Throwable cause) {
        super(cause);
    }

}
