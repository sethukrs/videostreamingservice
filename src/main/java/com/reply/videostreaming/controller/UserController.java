package com.reply.videostreaming.controller;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.reply.videostreaming.domain.User;
import com.reply.videostreaming.exception.UserUnderAgeException;
import com.reply.videostreaming.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Controller to expose endpoints for user creation and retrieval
 * @author sraamasubbu
 */

@RestController
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "Save a user")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "User created"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 403, message = "User under age of 18"),
        @ApiResponse(code = 409, message = "Username already exists")
    })
    @RequestMapping(value = "/videostreamingservice/users", method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveUser(@Valid @RequestBody User user) {

        long age = LocalDate.from(user.getDateOfBirth()).until(LocalDate.now(), ChronoUnit.YEARS);
        if (age < 18) {
            LOGGER.debug("User under age of 18, username:{}", user.getUsername());
            throw new UserUnderAgeException("User is under 18 years of age");
        }
        userService.saveUser(user);
        LOGGER.debug("User created with username:{}", user.getUsername());
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @ApiOperation(value = "Retrieve users")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 400, message = "Bad request"),
    })
    @RequestMapping(value = "/videostreamingservice/users", method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> retrieveUser(@RequestParam(value = "CreditCard", required = false) String filterByCreditCard) {
        if (StringUtils.isEmpty(filterByCreditCard)) {
            return userService.retrieveUsers();
        } else if ("Yes".equalsIgnoreCase(filterByCreditCard) || "No".equalsIgnoreCase(filterByCreditCard)) {
            return userService.retrieveUsers(filterByCreditCard);
        } else {
            throw new IllegalArgumentException("Invalid filterByCreditCard value");
        }
    }

}
