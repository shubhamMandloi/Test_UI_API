Feature: CoinMarketCap UI Testing

  @FrontEnd @displayRows
  Scenario: Verify 50 rows displayed
    Given I open the CoinMarketCap homepage
    When I select "Show rows" dropdown with value "50"
    Then I verify that 50 rows are displayed

  @FrontEnd @Filter
  Scenario: Apply Filters on CoinMarketCap
    Given I open the CoinMarketCap homepage
    When I click on the "Filters" button
    And I apply filters with "Market Cap" = "$1B - $10B" and "Price" = "$101 - $1,000"
    Then I verify the records displayed are correct based on the applied filter
