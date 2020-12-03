package com.reply.videostreaming.service;

/**
 * interface for payment service
 * @author sraamasubbu
 */
public interface PaymentService {

    void makePayment(String creditCardNo, int amount);
}
