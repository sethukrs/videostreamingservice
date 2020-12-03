package com.reply.videostreaming.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import com.reply.videostreaming.domain.Payment;

/**
 * Implementation of in memory payment repository using java collections
 * @author sraamasubbu
 */
@Repository
public class InMemoryPaymentRepository implements PaymentRepository<Payment> {

    private Map<String, Integer> paymentsMap = new ConcurrentHashMap<>();

    /**
     * store all payments being made for a credit card into Map<CreditCardNo, Amount>
     * @param payment
     */
    @Override
    public void save(Payment payment) {
        //if payment data for CreditCard already exist, add the new amount to the old
        if (paymentsMap.containsKey(payment.getCreditCardNo())) {
            paymentsMap.put(payment.getCreditCardNo(),
                (paymentsMap.get(payment.getCreditCardNo()) + payment.getAmount()));
        } else {
            //create new payment data with CC no and amount
            paymentsMap.put(payment.getCreditCardNo(), payment.getAmount());
        }
    }
}
