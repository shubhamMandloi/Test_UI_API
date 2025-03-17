Feature: CoinMarketCap UI Testing

  @UI @displayRows @TEST
  Scenario: Verify 50 rows displayed
    Given I open the CoinMarketCap homepage
    When I select "Show rows" dropdown with value "50"
    Then I verify that 50 rows are displayed

  @UI @Filter @TEST
  Scenario: Apply Filters on CoinMarketCap
    Given I open the CoinMarketCap homepage
    When I click on the "Filters" button
    And I apply filters with "Market Cap" = "$1B - $10B" and "Price" = "$101 - $1,000"
    Then I verify the records displayed are correct based on the applied filter


  @UI @TestAutomate @TEST
  Scenario: Navigate and capture product and price
    Given I open the TestAutomation homepage
    When I click on the label
    Then I fetch and navigate to all product and price



