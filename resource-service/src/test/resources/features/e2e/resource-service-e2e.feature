Feature: resource processing e2e

  @E2ETest
  Scenario: Client makes a call to make a new song resource
    When a client makes a POST request to '/api/v1/resources' endpoint
    Then a client receives status code 201 OK
    And a client receives the resource id
