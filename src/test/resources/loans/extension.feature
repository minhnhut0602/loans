Feature: Extension

  Scenario: Valid extension
    Given application is performed from ip "192.168.0.2"
    When user applies for a loan with amount 30 and with term 30
    And user tries to perform an extension
    Then  response status message is "Okay"

  Scenario: Trying to extend a loan which already has been extended
    When user tries to perform an extension
    Then response status message is "The loan is already extended"
