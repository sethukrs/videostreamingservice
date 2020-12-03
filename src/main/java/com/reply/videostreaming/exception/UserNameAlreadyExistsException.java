package com.reply.videostreaming.exception;

/**
 * User with username already exists exception
 * @author sraamasubbu
 */
public class UserNameAlreadyExistsException extends RuntimeException {

    public UserNameAlreadyExistsException() {
    }

    public UserNameAlreadyExistsException(String message) {
        super(message);
    }

    public UserNameAlreadyExistsException(Throwable cause) {
        super(cause);
    }

}
