Feature: Application

  Scenario: I apply for a loan
    Given My ip address is "192.168.0.0"
    When I apply from with amount 30 and with term 30
    Then I expect to receive a response "Okay"
