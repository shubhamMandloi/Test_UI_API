package StepDefinition;

import Utility.SeleniumUtils;
import io.cucumber.java.en.*;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class UI_StepDef {
    WebDriver driver;

    @Given("I open the CoinMarketCap homepage")
    public void openHomePage() {
        driver = new ChromeDriver();
        driver.get("https://coinmarketcap.com/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @When("I select {string} dropdown with value {string}")
    public void selectRows(String dropdown, String value) {
        WebElement showRows = driver.findElement(By.xpath("//span[.='Show rows']"));

        Actions action = new Actions(driver);
        action.moveToElement(showRows).build().perform();
        //SeleniumUtils.click(driver, showRows);
//        WebElement rowsDropdown = driver.findElement(By.xpath("//span[.='Show rows']/following-sibling::div")).click();
        driver.findElement(By.xpath("//span[.='Show rows']/following-sibling::div")).click();
        //WebElement rowsDropdown = driver.findElement(By.xpath("//div[.='100']"));
        //action.moveToElement(rowsDropdown).build().perform();
        //SeleniumUtils.click(driver, rowsDropdown);
        WebElement valueRows = driver.findElement(By.xpath("//button='"+value+"'"));
    }

    @Then("I verify that {int} rows are displayed")
    public void verifyRowsDisplayed(int expectedRows) {
        List<WebElement> rowsList = driver.findElements(By.xpath("//table[1]//tbody/tr"));
        Assert.assertEquals(expectedRows, rowsList.size());
    }

    @When("I click on the {string} button")
    public void clickFiltersButton(String button) {
        WebElement filtersButton = driver.findElement(By.xpath("(//button[.='Filters'])[2]"));
        SeleniumUtils.click(driver, filtersButton);
    }

    @When("I apply filters with {string} = {string} and {string} = {string}")
    public void applyFilters(String filterType1, String filterValue1, String filterType2, String filterValue2) {
        // Add logic to apply MarketCap and Price filters
        WebElement addFilter = driver.findElement(By.xpath("//button[normalize-space()='+ Add Filter']"));
        SeleniumUtils.click(driver, addFilter);


        WebElement filter1 = driver.findElement(By.xpath("//button[.='" + filterType1 + "']"));
        SeleniumUtils.click(driver, filter1);
        WebElement filterVal1 = driver.findElement(By.xpath("//button[.='" + filterValue1 + "']"));
        SeleniumUtils.click(driver, filterVal1);

        WebElement apply = driver.findElement(By.xpath("//button[contains(@data-qa-id,'cancel')]"));
        SeleniumUtils.click(driver, apply);

        WebElement filter2 = driver.findElement(By.xpath("//button[.='" + filterType2 + "']"));
        SeleniumUtils.click(driver, filter2);
        WebElement filterVal2 = driver.findElement(By.xpath("//button[.='" + filterValue2 + "']"));
        SeleniumUtils.click(driver, filterVal2);

        apply = driver.findElement(By.xpath("//button[contains(@data-qa-id,'cancel')]"));
        SeleniumUtils.click(driver, apply);

        WebElement showResult = driver.findElement(By.xpath("//button[.='Show results']"));
        SeleniumUtils.click(driver, showResult);


    }

    @Then("I verify the records displayed are correct based on the applied filter")
    public void verifyFilteredRecords() {
        WebElement filterCheck = driver.findElement(By.xpath("//li[contains(.,'2 More Filters')]"));

        List<WebElement> priceList = driver.findElements(By.xpath("//table/tbody/tr/td[4]//span"));
        for (WebElement eachPrice : priceList) {
            double priceValue = Double.parseDouble(eachPrice.getText()
                            .replaceAll("\\$", ""));
            if (!(priceValue > 100 && priceValue < 1000)) {
                Assert.fail(" Price value : " + priceValue + " is not in filter range");
            }
        }


        List<WebElement> marketCaps = driver.findElements(By.xpath("//table/tbody/tr/td[8]/p/span[1]"));
        for (WebElement eachMarketCap : marketCaps) {
            double marketCapValue = Double.parseDouble(eachMarketCap
                    .getText()
                    .replaceAll("\\$", "")
                    .replaceAll("B", ""));
            if (!(marketCapValue > 1 && marketCapValue < 10)) {
                Assert.fail(" Market Cap value : " + marketCapValue + " is not in filter range");
            }
        }


    }
}
