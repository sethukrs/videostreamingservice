package com.reply.videostreaming.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.reply.videostreaming.domain.User;
import com.reply.videostreaming.exception.UserNameAlreadyExistsException;
import com.reply.videostreaming.service.UserService;

/**
 * Tests for {@link UserController}
 * @author sraamasubbu
 */
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userServiceMock;

    @Test
    public void should_CreateUser_When_ValidRequest() throws Exception {
        doNothing().when(userServiceMock).saveUser(Mockito.any(User.class));

        mockMvc.perform(post("/videostreamingservice/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\n"
                + "  \"creditCardNo\": \"1234000012340000\",\n"
                + "  \"dateOfBirth\": \"2000-12-12\",\n"
                + "  \"email\": \"test@gmail.com\",\n"
                + "  \"password\": \"Password1\",\n"
                + "  \"username\": \"user001\"\n"
                + "}")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().is(201));
    }

    @Test
    public void should_Return_403_When_User_Age_Below_18() throws Exception {

        mockMvc.perform(post("/videostreamingservice/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\n"
                + "  \"creditCardNo\": \"1234000012340000\",\n"
                + "  \"dateOfBirth\": \"2020-12-12\",\n"
                + "  \"email\": \"test@gmail.com\",\n"
                + "  \"password\": \"Password1\",\n"
                + "  \"username\": \"user001\"\n"
                + "}")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().is(403));
    }

    @Test
    public void should_Return_409_When_UserName_Already_Used() throws Exception {

        doThrow(UserNameAlreadyExistsException.class).when(userServiceMock).saveUser(Mockito.any(User.class));

        mockMvc.perform(post("/videostreamingservice/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\n"
                + "  \"creditCardNo\": \"1234000012340000\",\n"
                + "  \"dateOfBirth\": \"2000-12-12\",\n"
                + "  \"email\": \"test@gmail.com\",\n"
                + "  \"password\": \"Password1\",\n"
                + "  \"username\": \"user001\"\n"
                + "}")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().is(409));
    }

    @Test
    public void should_Return_400_When_CreditCard_Invalid() throws Exception {

        mockMvc.perform(post("/videostreamingservice/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\n"
                + "  \"creditCardNo\": \"invalid\",\n"
                + "  \"dateOfBirth\": \"2000-12-12\",\n"
                + "  \"email\": \"test@gmail.com\",\n"
                + "  \"password\": \"Password1\",\n"
                + "  \"username\": \"user001\"\n"
                + "}")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().is(400));
    }

    @Test
    public void should_Return_400_When_Password_Invalid() throws Exception {

        mockMvc.perform(post("/videostreamingservice/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\n"
                + "  \"creditCardNo\": \"1234000012340000\",\n"
                + "  \"dateOfBirth\": \"2000-12-12\",\n"
                + "  \"email\": \"test@gmail.com\",\n"
                + "  \"password\": \"password\",\n"
                + "  \"username\": \"user001\"\n"
                + "}")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().is(400));
    }

    @Test
    public void should_Return_400_When_Email_Invalid() throws Exception {

        mockMvc.perform(post("/videostreamingservice/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\n"
                + "  \"creditCardNo\": \"1234000012340000\",\n"
                + "  \"dateOfBirth\": \"2000-12-12\",\n"
                + "  \"email\": \"test\",\n"
                + "  \"password\": \"passworD1\",\n"
                + "  \"username\": \"user001\"\n"
                + "}")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().is(400));
    }

    @Test
    public void should_Return_400_When_dateOfBirth_Invalid() throws Exception {

        mockMvc.perform(post("/videostreamingservice/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\n"
                + "  \"creditCardNo\": \"1234000012340000\",\n"
                + "  \"dateOfBirth\": \"2000-DEC-12\",\n"
                + "  \"email\": \"test@gmail.com\",\n"
                + "  \"password\": \"passworD1\",\n"
                + "  \"username\": \"user001\"\n"
                + "}")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().is(400));
    }
}

