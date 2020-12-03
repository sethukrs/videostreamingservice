package com.reply.videostreaming.repository;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.reply.videostreaming.domain.User;
import com.reply.videostreaming.exception.UserNameAlreadyExistsException;

/**
 * Implementation of in memory user repository using java collections
 * @author sraamasubbu
 */
@Repository
public class InMemoryUserRepository implements UserRepository<User> {
    private Map<String, User> usersMap = new ConcurrentHashMap<>();
    private Set<String> creditCardSet = new HashSet();

    /**
     * Store the user entity. save the credit number in a set for faster payment checks
     * @param user
     */
    @Override
    public void save(User user) {
        if (usersMap.get(user.getUsername()) != null) {
            throw new UserNameAlreadyExistsException();
        } else {
            usersMap.put(user.getUsername(), user);
            //add credit card no to set for quicker payments
            creditCardSet.add(user.getCreditCardNo());
        }
    }

    /**
     * Retrieve all users from datastore.
     * @return all users
     */
    @Override
    public List<User> findAll() {
        return usersMap.values().stream().collect(Collectors.toList());
    }

    /**
     * Retrieve users based on filter
     * @param hasCreditCard if true, retrieve all users that have a credit card, if false, viceversa.
     * @return user list
     */
    @Override
    public List<User> findByCreditCardPresent(boolean hasCreditCard) {
        return usersMap.values().stream().filter(e -> hasCreditCard ?
               !StringUtils.isEmpty(e.getCreditCardNo()) && hasCreditCard : StringUtils.isEmpty(e.getCreditCardNo()) && !hasCreditCard
        ).collect(Collectors.toList());
    }

    /**
     * Checks if the supplied credit card has been registered to any user
     * @param creditCardNo
     * @return true/false
     */
    @Override
    public boolean isCreditCardRegistered(String creditCardNo) {
        return creditCardSet.contains(creditCardNo);
    }

}
