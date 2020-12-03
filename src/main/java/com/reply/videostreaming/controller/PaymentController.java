package com.reply.videostreaming.controller;


import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.reply.videostreaming.domain.Payment;
import com.reply.videostreaming.service.PaymentService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Controller to expose endpoints for payments
 * @author sraamasubbu
 */
@RestController
public class PaymentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentController.class);

    private PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @ApiOperation(value = "Make a payment")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Payment made"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 404, message = "Credit card not registered with any user"),
    })
    @RequestMapping(value = "/videostreamingservice/payments", method = RequestMethod.POST,
        consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveUser(@Valid @RequestBody Payment payment) {
        paymentService.makePayment(payment.getCreditCardNo(), payment.getAmount());
        LOGGER.debug("payment successful");
        return new ResponseEntity(HttpStatus.CREATED);
    }



}
