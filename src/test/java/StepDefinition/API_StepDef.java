package StepDefinition;

import Utility.PropertiesReader;

import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.io.IOException;
import java.util.Properties;

import static org.junit.Assert.assertNotNull;

public class API_StepDef {

    Properties prop = new PropertiesReader().loadProperties("src/test/resource/Config.properties");
    private final String API_KEY = prop.get("API_KEY").toString();

    private int btcID, usdtID, ethID;
    private float btcPriceInBOB, usdtPriceInBOB, ethPriceInBOB;

    public API_StepDef() throws IOException {
    }

    @Given("I have the CoinMarketCap API key")
    public void i_have_the_coinmarketcap_api_key() {
        assertNotNull("API Key is required", API_KEY);
        RestAssured.baseURI = "https://pro-api.coinmarketcap.com";
    }

    @When("I retrieve the ID of bitcoin, usd tether, and Ethereum")
    public void i_retrieve_cryptocurrency_ids() {
        Response response = RestAssured.given()
                .header("X-CMC_PRO_API_KEY", API_KEY)
                .queryParam("symbol", "BTC,USDT,ETH")
                .get("/v1/cryptocurrency/map");

        // Extract and save the IDs for BTC, USDT, and ETH
        btcID = response.jsonPath().getInt("data.find { it.symbol == 'BTC' }.id");
        usdtID = response.jsonPath().getInt("data.find { it.symbol == 'USDT' }.id");
        ethID = response.jsonPath().getInt("data.find { it.symbol == 'ETH' }.id");

        assertNotNull("BTC ID is required", btcID);
        assertNotNull("USDT ID is required", usdtID);
        assertNotNull("ETH ID is required", ethID);
    }

    @Then("I convert these IDs to Bolivian Boliviano")
    public void i_convert_ids_to_bolivian_boliviano() {
        btcPriceInBOB = convertToBoliviano(btcID);
        usdtPriceInBOB = convertToBoliviano(usdtID);
        ethPriceInBOB = convertToBoliviano(ethID);
    }

    @Then("I verify the price conversion for each cryptocurrency")
    public void i_verify_price_conversion() {
        System.out.println("BTC Price in BOB: " + btcPriceInBOB);
        System.out.println("USDT Price in BOB: " + usdtPriceInBOB);
        System.out.println("ETH Price in BOB: " + ethPriceInBOB);

        // You can add assertions to verify the prices are returned correctly
        assertNotNull("BTC Price in BOB should not be null", btcPriceInBOB);
        assertNotNull("USDT Price in BOB should not be null", usdtPriceInBOB);
        assertNotNull("ETH Price in BOB should not be null", ethPriceInBOB);
    }

    private float convertToBoliviano(int currencyID) {
        Response response = RestAssured.given()
                .header("X-CMC_PRO_API_KEY", API_KEY)
                .queryParam("id", currencyID)
                .queryParam("convert", "BOB")
                .get("/v1/tools/price-conversion");

        return response.jsonPath().getFloat("data.quote.BOB.price");
    }
}
