@API
Feature: Users / Payments API - tests

  Scenario Outline: User API creates users successfully
    Given the user username is "<username>"
      And the user password is "<password>"
      And the user email is "<email>"
      And the user date of birth is "<dob>"
      And the user credit card number is "<creditCardNo>"
    When users endpoint is invoked to create the user
    Then the client receives status code of 201

    Examples:
      | username | password | email            | dob        | creditCardNo     |
      | user001  | Passw0rd | user001@test.com | 2000-12-12 | 1234000012340000 |
      | user002  | Passw0rd | user002@test.com | 2000-12-12 |  |
      | user003  | Passw0rd | user003@test.com | 2000-12-12 | 1234000012340001 |

  Scenario Outline: User API creates users handle error scenarios
    Given the user username is "<username>"
    And the user password is "<password>"
    And the user email is "<email>"
    And the user date of birth is "<dob>"
    And the user credit card number is "<creditCardNo>"
    When users endpoint is invoked to create the user
    Then the client receives status code of <statusCode>

    Examples:
      | username | password | email            | dob        | creditCardNo     | statusCode |
      | user001  | Passw0rd | user001@test.com | 2019-12-12 | 1234000012340000 | 403        |
      | user001  | Passw0rd | user002@test.com | 2000-12-12 | 1234000012340000 | 409        |
      | user002  | Passw0rd | user003@test.com | 2000-12-12 | invalid          | 400        |
      | user003  | Passw0rd | user003@test.com | 2000-12-12 | 12340000         | 400        |
      | user004  | invalid  | user003@test.com | 2000-12-12 | 1234000012340000 | 400        |
      | inv****  | Passw0rd | user002@test.com | 2000-12-12 | 1234000012340000 | 400        |
      | user005  | Passw0rd | user005@test.com | invalid    | 1234000012340000 | 400        |

  Scenario Outline: User API returns correct list of users
    Given the filter by credit card is "<filterByCreditCard>"
    When users endpoint is invoked to retrieve users
    Then the client receives status code of 200
    Then the list of users retrieved is <noOfUsers>

    Examples:
      | filterByCreditCard | noOfUsers |
      | Yes                | 2         |
      | No                 | 1         |
      |                    | 3         |

  Scenario Outline: Payments API makes payments successfully - Registered Credit Card
    Given the credit card number is "<creditCardNo>"
    And the payment amount is <amount>
    When payments endpoint is invoked to make the payment
    Then the client receives status code of 201

    Examples:
      | creditCardNo     | amount |
      | 1234000012340000 | 100    |

  Scenario Outline: Payments API handles error scenarios
    Given the credit card number is "<creditCardNo>"
    And the payment amount is <amount>
    When payments endpoint is invoked to make the payment
    Then the client receives status code of <statusCode>

    Examples:
      | creditCardNo     | amount  | statusCode |
      | 1111222211112222 | 100     | 404        |
      | 1234000012340000 | 1000    | 400        |
      | 1234000012340000 | 0       | 400        |
      | invalid          | 100     | 400        |