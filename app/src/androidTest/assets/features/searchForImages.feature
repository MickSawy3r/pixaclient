Feature: Search for images, and View the Details

  Scenario: Request search with a certain query
    Given the app is running
    When I search for a query "Dogs"
    Then I should see images as a result to this search

  Scenario: When offline show the cached images
    Given the app is running
    When I search for a query "Dogs"
    Then I should see images as a result to this search
    Given I have viewed the first image
    When I request the cached images
    Then I should see the image I viewed earlier

  Scenario: I see a list of images, I wanted to view a certain one
    Given the app is running
    When I search for a query "Dogs"
    Then I should see images as a result to this search
    When I click the first image on the screen
    Then I see a dialog asking me if I wanted to view details
    Given I confirm wanting to see the image details
    Then I should see a new page with the details of that image