Feature: Resource Storage

  @ComponentTest
  Scenario: Client makes a request to upload multipart file
    When the client makes PUT request to '/api/v1/resources'
    Then the client receives status code 201