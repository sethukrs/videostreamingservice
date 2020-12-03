package com.reply.videostreaming.cucumber.stepdefs;

import static io.restassured.RestAssured.given;

import java.net.URI;
import java.net.URISyntaxException;

import org.hamcrest.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.reply.videostreaming.utils.MockFileReader;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import io.restassured.http.ContentType;

/**
 * User Step Definitions
 * @author sraamasubbu
 */
public class UserStepDefinitions extends CucumberStepDefinitions {

    @Value("${test.hostname}")
    protected String baseUri;

    @Value("${server.port}")
    protected Integer serverPort;

    private static final String USER_BASE_URI = "/videostreamingservice/users";
    public static final String CREATE_USER_REQUEST_PATH = "json/create_users_request.json";

    private static final String PAYMENT_BASE_URI = "/videostreamingservice/payments";
    public static final String MAKE_PAYMENT_REQUEST_PATH = "json/make_payment_request.json";

    @Value("${test.hostname}")
    private String hostname;

    @Autowired
    private MockFileReader fileReader;

    @Autowired
    private CommonVariableRepository commonVariableRepository;

    @Before
    public void setup() {
        commonVariableRepository.setDefaultValues();
    }

    @Then("^the client receives status code of (\\d+)$")
    public void the_client_receives_status_code_of(int expectedStatusCode) {
        commonVariableRepository.getResponse().statusCode(expectedStatusCode);
    }

    @When("^users endpoint is invoked to create the user$")
    public void userIsCreated() throws URISyntaxException {
        String body = String.format(fileReader.readFile(CREATE_USER_REQUEST_PATH),
            commonVariableRepository.getUsername(),
            commonVariableRepository.getPassword(),
            commonVariableRepository.getEmail(),
            commonVariableRepository.getDateOfBirth(),
            commonVariableRepository.getCreditCardNo());
        sendCreateUserRequest(body);
    }

    @Given("the user username is \"(.*)\"$")
    public void theUserUsernameIsUsername(String username) {
        commonVariableRepository.setUsername(username);
    }

    @And("the user password is \"(.*)\"$")
    public void theUserPasswordIsPassword(String password) {
        commonVariableRepository.setPassword(password);
    }

    @And("the user email is \"(.*)\"$")
    public void theUserEmailIsEmail(String email) {
        commonVariableRepository.setEmail(email);
    }

    @And("the user date of birth is \"(.*)\"$")
    public void theUserDateOfBirthIsDateOfBirth(String dateOfBirth) {
        commonVariableRepository.setDateOfBirth(dateOfBirth);
    }

    @And("the user credit card number is \"(.*)\"$")
    public void theUserCreditCardNumberIsCreditCardNo(String creditCardNo) {
        commonVariableRepository.setCreditCardNo(creditCardNo);
    }

    @And("the filter by credit card is {string}")
    public void theFilterByCreditCardIs(String filterByCreditCard) {
        commonVariableRepository.setFilterByCreditCard(filterByCreditCard);
    }

    @When("users endpoint is invoked to retrieve users")
    public void usersEndpointIsInvokedToRetrieveUsers() throws URISyntaxException {
        sendRetrieveUsersRequest();
    }

    @Then("the list of users retrieved is {int}")
    public void theUserWithUsernameIsRetrieved(int noOfUsers) {
        commonVariableRepository.getResponse()
            .body("username.size", Matchers.equalTo(noOfUsers));
    }

    @When("^payments endpoint is invoked to make the payment$")
    public void paymentIsMade() throws URISyntaxException {
        String body = String.format(fileReader.readFile(MAKE_PAYMENT_REQUEST_PATH),
            commonVariableRepository.getAmount(),
            commonVariableRepository.getCreditCardNo());
        sendMakePaymentRequest(body);
    }

    @And("the payment amount is (\\d+)$")
    public void theUserUsernameIsUsername(int amount) {
        commonVariableRepository.setAmount(amount);
    }

    @Given("the credit card number is {string}")
    public void theUserCreditCardNumberIs(String creditCardNo) {
        commonVariableRepository.setCreditCardNo(creditCardNo);
    }

    public void sendCreateUserRequest(String body) throws URISyntaxException {
        URI requestUri = new URI(hostname + ":" + serverPort + USER_BASE_URI);
        commonVariableRepository.setResponse(given()
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
            .when()
            .body(body)
            .post(requestUri)
            .then());
    }

    private void sendRetrieveUsersRequest() throws URISyntaxException {
        URI requestUri = new URI(hostname + ":" + serverPort + USER_BASE_URI + "?CreditCard=" + commonVariableRepository.getFilterByCreditCard());
        commonVariableRepository.setResponse(given()
            .contentType(ContentType.JSON)
            .when()
            .get(requestUri)
            .then());
    }

    private void sendMakePaymentRequest(String body) throws URISyntaxException {
        URI requestUri = new URI(hostname + ":" + serverPort + PAYMENT_BASE_URI);
        commonVariableRepository.setResponse(given()
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
            .when()
            .body(body)
            .post(requestUri)
            .then());
    }

}
