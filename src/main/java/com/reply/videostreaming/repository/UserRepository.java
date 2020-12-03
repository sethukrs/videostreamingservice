package com.reply.videostreaming.repository;

import java.util.List;


import com.reply.videostreaming.domain.User;

/**
 * Repository interface for users
 * @author sraamasubbu
 */
public interface UserRepository<T> {

    void save(T t);

    List<T> findAll();

    List<T> findByCreditCardPresent(boolean hasCreditCard);

    boolean isCreditCardRegistered(String creditCardNo);

}
