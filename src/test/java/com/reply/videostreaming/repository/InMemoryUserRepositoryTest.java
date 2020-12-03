package com.reply.videostreaming.repository;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.google.common.collect.Lists;
import com.reply.videostreaming.domain.User;
import com.reply.videostreaming.exception.UserNameAlreadyExistsException;

/**
 * Tests for {@link InMemoryUserRepository}
 * @author sraamasubbu
 */
@RunWith(MockitoJUnitRunner.class)
public class InMemoryUserRepositoryTest {

    @InjectMocks
    private InMemoryUserRepository underTest;

    @Test
    public void testSaveAndRetrieveUserWithCreditCard() {
        //when
        underTest.save(userListWithCreditCard());

        //then
        assertEquals(Lists.newArrayList(userListWithCreditCard()), underTest.findByCreditCardPresent(true));
    }

    @Test
    public void testSaveAndRetrieveUserWithOutCreditCard() {
        //when
        underTest.save(userListWithOutCreditCard());

        //then
        assertEquals(Lists.newArrayList(userListWithOutCreditCard()), underTest.findByCreditCardPresent(false));
    }

    @Test
    public void testIsCreditCardRegistered() {
        //when
        underTest.save(userListWithCreditCard());

        //then
        assertEquals(true, underTest.isCreditCardRegistered("1234000012340000"));
        assertEquals(false, underTest.isCreditCardRegistered("1234000012340001"));
    }

    @Test(expected = UserNameAlreadyExistsException.class)
    public void testUserNameAlreadyExists() {
        //given
        underTest.save(userListWithCreditCard());

        //when
        underTest.save(userListWithCreditCard());

        //then THROWS exception
    }

    private User userListWithCreditCard() {
        return User.builder().username("user001").password("Password1").dateOfBirth(LocalDate.parse("2000-12-12"))
            .email("test@gmail.com").creditCardNo("1234000012340000").build();
    }

    private User userListWithOutCreditCard() {
        return User.builder().username("user002").password("Password1").dateOfBirth(LocalDate.parse("2000-12-12"))
            .email("test@gmail.com").build();
    }
}
