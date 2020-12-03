package com.reply.videostreaming.repository;

import com.reply.videostreaming.domain.Payment;

/**
 * Repository interface for payments
 * @author sraamasubbu
 */
public interface PaymentRepository<T> {

    void save(T t);

}
