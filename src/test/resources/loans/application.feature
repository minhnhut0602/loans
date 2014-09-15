Feature: Application

  Scenario: Valid application
    Given application is performed from ip "192.168.0.0"
    When user applies for a loan with amount 30 and with term 30
    Then response status message is "Okay"

  Scenario: Maximum applications reached
    Given application is performed from ip "192.168.0.1"
    When user applies 3 times for a loan with amount 30 and with term 30
    Then response status message is "Possible Spam"
