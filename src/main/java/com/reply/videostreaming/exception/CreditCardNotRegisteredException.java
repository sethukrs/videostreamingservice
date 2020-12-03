package com.reply.videostreaming.exception;

/**
 * Credit card not registered exception
 * @author sraamasubbu
 */
public class CreditCardNotRegisteredException extends RuntimeException {
    public CreditCardNotRegisteredException() {
    }

    public CreditCardNotRegisteredException(String message) {
        super(message);
    }

    public CreditCardNotRegisteredException(Throwable cause) {
        super(cause);
    }
}
