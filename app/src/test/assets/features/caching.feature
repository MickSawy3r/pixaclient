Feature: Caching Network Requests

  Scenario: Cache a Details Result
    Given I have an access to Pixabay Details API
    And I have accessed the details of the image with id 123
    When I disconnect from the internet
    And I request the cached images
    Then I should get a cached result that contains the image with id 123