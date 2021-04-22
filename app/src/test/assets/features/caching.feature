Feature: Caching Network Requests

  Scenario: Cache a Search Result
    Given I have an access to Pixabay API
    And I have accessed a search query of "fruit"
    When I disconnect from the internet
    And I search offline for the query "fruit"
    Then I should get a result
    And It should have the same length as the network request result list

  Scenario: Cache a Details Result
    Given I have an access to Pixabay API
    And I have accessed the details of the image with id 123
    When I disconnect from the internet
    And I access the same image of id 123
    Then I should get a cached result for the image with id 123