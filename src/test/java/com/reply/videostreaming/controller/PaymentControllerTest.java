package com.reply.videostreaming.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.reply.videostreaming.exception.CreditCardNotRegisteredException;
import com.reply.videostreaming.service.PaymentService;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * Tests for {@link PaymentController}
 * @author sraamasubbu
 */
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = PaymentController.class)
public class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentServiceMock;

    @Test
    public void should_MakePayment_When_ValidRequest() throws Exception {
        doNothing().when(paymentServiceMock).makePayment(Mockito.anyString(), Mockito.anyInt());

        mockMvc.perform(post("/videostreamingservice/payments")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{ \"amount\": 100,  \"creditCardNo\": \"1234000012340000\" }")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().is(201));
    }

    @Test
    public void should_Return_404_When_CreditCard_NotRegistered() throws Exception {
        doThrow(CreditCardNotRegisteredException.class).when(paymentServiceMock).makePayment(Mockito.anyString(), Mockito.anyInt());

        mockMvc.perform(post("/videostreamingservice/payments")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{ \"amount\": 100,  \"creditCardNo\": \"1234000012340000\" }")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().is(404));
    }

    @Test
    public void should_Return_400_When_CreditCard_Invalid() throws Exception {

        mockMvc.perform(post("/videostreamingservice/payments")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{ \"amount\": 100,  \"creditCardNo\": \"invalid\" }")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().is(400));
    }

    @Test
    public void should_Return_400_When_Amount_GreaterThan_3_Digit() throws Exception {

        mockMvc.perform(post("/videostreamingservice/payments")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{ \"amount\": 1000,  \"creditCardNo\": \"1234000012340000\" }")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().is(400));
    }
}
