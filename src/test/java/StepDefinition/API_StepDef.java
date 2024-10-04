package StepDefinition;

import Utility.PropertiesReader;

import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;

import java.io.IOException;
import java.util.Properties;

import static org.junit.Assert.assertNotNull;

public class API_StepDef {


    //Properties prop = new PropertiesReader().loadProperties("src/test/resource/Config.properties");
    //private  String API_KEY = prop.get("API_KEY").toString();
    final String API_KEY = "28e1df85-907b-4f75-af08-bf4e94836c42";
    private int btcID, usdtID, ethID,bobID;
    private float btcPriceInBOB, usdtPriceInBOB, ethPriceInBOB;


    @Given("I have the CoinMarketCap API key")
    public void i_have_the_coinmarketcap_api_key() {
        assertNotNull("API Key is required", API_KEY);
        RestAssured.baseURI = "https://pro-api.coinmarketcap.com";
    }

    @When("I retrieve the ID of bitcoin, usd tether, and Ethereum")
    public void i_retrieve_cryptocurrency_ids() {
        Response response = RestAssured.given()
                .header("X-CMC_PRO_API_KEY", API_KEY)
                .queryParam("symbol", "BTC,USDT,ETH,BOB")
                .get("/v1/cryptocurrency/map");


        btcID = response.jsonPath().getInt("data.find { it.symbol == 'BTC' }.id");
        usdtID = response.jsonPath().getInt("data.find { it.symbol == 'USDT' }.id");
        ethID = response.jsonPath().getInt("data.find { it.symbol == 'ETH' }.id");
        bobID = response.jsonPath().getInt("data.find { it.symbol == 'BOB' }.id");


        assertNotNull("BTC ID is required", btcID);
        assertNotNull("USDT ID is required", usdtID);
        assertNotNull("ETH ID is required", ethID);
        assertNotNull("BOB ID is required", bobID);
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


        assertNotNull("BTC Price in BOB should not be null", btcPriceInBOB);
        assertNotNull("USDT Price in BOB should not be null", usdtPriceInBOB);
        assertNotNull("ETH Price in BOB should not be null", ethPriceInBOB);
    }

    private float convertToBoliviano(int currencyID) {
        Response response2 = RestAssured.given()
                .header("X-CMC_PRO_API_KEY", API_KEY)
                .queryParam("id", currencyID)
                .queryParam("convert","BOB")
                .queryParam("amount",1)
                .get("/v2/tools/price-conversion");

       // String res = response2.jsonPath().getString("status.error_message");
   return response2.jsonPath().getFloat("data.quote.BOB.price");
    }
}
