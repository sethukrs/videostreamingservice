package com.reply.videostreaming.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reply.videostreaming.domain.Payment;
import com.reply.videostreaming.exception.CreditCardNotRegisteredException;
import com.reply.videostreaming.repository.PaymentRepository;
import com.reply.videostreaming.repository.UserRepository;

/**
 * Implementation of payment service
 * @author sraamasubbu
 */
@Service
public class PaymentServiceImpl implements PaymentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentServiceImpl.class);

    private UserRepository userRepository;
    private PaymentRepository paymentRepository;

    @Autowired
    public PaymentServiceImpl(UserRepository userRepository, PaymentRepository paymentRepository) {
        this.userRepository = userRepository;
        this.paymentRepository = paymentRepository;
    }

    /**
     * Make a payment for creditcard
     * @param creditCardNo
     * @param amount
     */
    @Override
    public void makePayment(String creditCardNo, int amount) {
        if ( !userRepository.isCreditCardRegistered(creditCardNo)) {
            LOGGER.error("Credit card not registered");
            throw new CreditCardNotRegisteredException();
        } else {
            LOGGER.debug("Payment made, amount:{}", amount);
            paymentRepository.save(
                Payment.builder().creditCardNo(creditCardNo).amount(amount).build());
        }
    }
}
