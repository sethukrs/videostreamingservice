package com.reply.videostreaming.service;

import java.util.List;

import com.reply.videostreaming.domain.User;

/**
 * interface for user service
 * @author sraamasubbu
 */
public interface UserService {

    void saveUser(User user);

    List<User> retrieveUsers();

    List<User> retrieveUsers(String filterByCreditCard);
}
