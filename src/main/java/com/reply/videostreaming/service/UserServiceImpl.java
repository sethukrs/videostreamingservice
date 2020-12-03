package com.reply.videostreaming.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.reply.videostreaming.domain.User;
import com.reply.videostreaming.repository.UserRepository;

/**
 * Implementation class for user service
 * @author sraamasubbu
 */
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    /**
     * retrives all users from data store
     * @return
     */
    @Override
    public List<User> retrieveUsers() {
        return userRepository.findAll();
    }

    /**
     * Retrieve users based on filter
     * @param filterByCreditCard if yes, retrieve all users that have a credit card, if no, viceversa.
     * @return user list
     */
    @Override
    public List<User> retrieveUsers(String filterByCreditCard) {
        if ("Yes".equalsIgnoreCase(filterByCreditCard)) {
            return userRepository.findByCreditCardPresent(true);
        } else {
            return userRepository.findByCreditCardPresent(false);
        }
    }
}
