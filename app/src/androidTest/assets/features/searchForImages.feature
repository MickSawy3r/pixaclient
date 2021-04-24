Feature: Search for images, and View the Details

  Scenario: Request search with a certain query
    Given the app is running
    When I search for a query "Dogs"
    Then I should see images as a result to this search

  Scenario: When offline show the cached images
    Given I have viewed images previously
    When I open the app
    Then I should see the cached images
    And The search bar should be disabled
    When I click the first image on the list
    Then I should see a new page with the details of that image

  Scenario: I see a list of images, I wanted to view a certain one
    Given the app is running
    And I see a list of images
    When I click the first image on the screen
    Then I see a dialog asking me if I wanted to view details
    Given I confirm
    Then I should see a new page with the details of that image