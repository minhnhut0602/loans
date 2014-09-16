Feature: Request for application history

  Scenario: Valid history request
    When user requests for history
    Then response status message is "Okay"