package com.reply.videostreaming.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.reply.videostreaming.exception.CreditCardNotRegisteredException;
import com.reply.videostreaming.exception.UserNameAlreadyExistsException;
import com.reply.videostreaming.exception.UserUnderAgeException;

/**
 * Controller Exception Handler
 * @author sraamasubbu
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(CreditCardNotRegisteredException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleProfileNotFoundException(CreditCardNotRegisteredException e) {
        LOGGER.error("Credit card not registered", e);
    }

    @ExceptionHandler(UserNameAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public void handleUserNameAlreadyExistsException(UserNameAlreadyExistsException e) {
        LOGGER.error("Username already exists", e);
    }

    @ExceptionHandler(UserUnderAgeException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public void handleUserUnderAgeException(UserUnderAgeException e) {
        LOGGER.error("User is under 18 years of age", e);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleIllegalArgumentException(IllegalArgumentException e) {
        LOGGER.error("invalid filter value", e);
    }

}
