Feature: Pixabay Client

  Scenario: Search for images in Pixabay
    Given I have an access to Pixabay API
    When I request a search "Fruits"
    Then I get an images list result