package com.reply.videostreaming.cucumber.stepdefs;

import org.springframework.stereotype.Repository;

import io.restassured.response.ValidatableResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Utility class with common variables for users api test
 * @author sraamasubbu
 */
@Repository
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CommonVariableRepository {

    private String username;
    private String password;
    private String email;
    private String dateOfBirth;
    private String creditCardNo;
    private int amount;

    private ValidatableResponse response;

    private String filterByCreditCard;

    public void setDefaultValues() {
        username = "";
        password = "";
        email = "";
        dateOfBirth = "";
        creditCardNo = "";
        amount = 0;

        response = null;
        filterByCreditCard = "";
    }
}
