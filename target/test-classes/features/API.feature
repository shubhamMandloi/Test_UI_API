Feature: CoinMarketCap API Testing

  Scenario: Retrieve and convert cryptocurrency IDs
    Given I have the CoinMarketCap API key
    When I retrieve the ID of bitcoin, usd tether, and Ethereum
    Then I convert these IDs to Bolivian Boliviano
    And I verify the price conversion for each cryptocurrency
