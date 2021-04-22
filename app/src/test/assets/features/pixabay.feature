Feature: Pixabay Client

  Scenario: Search for images in Pixabay
    Given I have an access to Pixabay API
    When I request a search "Fruits"
    Then I get an images list result

  Scenario: Get Image Details
    Given I have an access to Pixabay API
    When I request an image with a valid id 195893
    Then I should get an Image from user "Josch13"