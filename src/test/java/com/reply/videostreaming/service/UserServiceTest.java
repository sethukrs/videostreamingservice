package com.reply.videostreaming.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.reply.videostreaming.domain.User;
import com.reply.videostreaming.repository.UserRepository;

/**
 * Test for {@link UserServiceImpl}
 * @author sraamasubbu
 */
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl underTest;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Test
    public void testSaveUser() {
        //given
        doNothing().when(userRepository).save(userArgumentCaptor.capture());

        //when
        User user = User.builder().username("user001").password("Password1").dateOfBirth(LocalDate.parse("2000-12-12"))
            .email("test@gmail.com").build();
        underTest.saveUser(user);

        //then
        assertThat(userArgumentCaptor.getValue(), samePropertyValuesAs(user));
    }

    @Test
    public void testRetrieveUsers() {

        //given
        doReturn(userListWithCreditCard()).when(userRepository).findByCreditCardPresent(eq(true));
        doReturn(userListWithOutCreditCard()).when(userRepository).findByCreditCardPresent(eq(false));
        doReturn(Lists.list(userListWithCreditCard(), userListWithOutCreditCard())).when(userRepository).findAll();

        //when
        List<User> userListWithCC = underTest.retrieveUsers("Yes");
        List<User> userListWithOutCC = underTest.retrieveUsers("No");
        List<User> userList = underTest.retrieveUsers();

        //then
        assertEquals(userListWithCreditCard(), userListWithCC);
        assertEquals(userListWithOutCreditCard(), userListWithOutCC);
        assertEquals(2, userList.size());

    }

    @Test
    public void testRetrieveUsers_1() {

        //given
        doReturn(Lists.emptyList()).when(userRepository).findByCreditCardPresent(eq(true));
        doReturn(userListWithOutCreditCard()).when(userRepository).findByCreditCardPresent(eq(false));

        //when
        List<User> userListWithCC = underTest.retrieveUsers("Yes");
        List<User> userListWithOutCC = underTest.retrieveUsers("No");
        List<User> userList = underTest.retrieveUsers("ALL");

        //then
        assertEquals(0, userListWithCC.size());
        assertEquals(userListWithOutCreditCard(), userListWithOutCC);
        assertEquals(1, userList.size());

    }

    private List<User> userListWithCreditCard() {
        return Collections.singletonList(User.builder().username("user001").password("Password1").dateOfBirth(LocalDate.parse("2000-12-12"))
            .email("test@gmail.com").creditCardNo("1234000012340000").build());
    }

    private List<User> userListWithOutCreditCard() {
        return Collections.singletonList(User.builder().username("user002").password("Password1").dateOfBirth(LocalDate.parse("2000-12-12"))
            .email("test@gmail.com").build());
    }

}
