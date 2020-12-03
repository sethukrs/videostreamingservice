package com.reply.videostreaming.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.reply.videostreaming.exception.CreditCardNotRegisteredException;
import com.reply.videostreaming.repository.UserRepository;

/**
 * Test for {@link PaymentServiceImpl}
 * @author sraamasubbu
 */
@RunWith(MockitoJUnitRunner.class)
public class PaymentServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PaymentServiceImpl underTest;

    @Test(expected = CreditCardNotRegisteredException.class)
    public void testMakePaymentWhenCreditCardNotRegistered() {
        //given
        doReturn(false).when(userRepository).isCreditCardRegistered(any());

        //when
        underTest.makePayment("1234000012340000", 100);

        //then THROWS Exception

    }
}
