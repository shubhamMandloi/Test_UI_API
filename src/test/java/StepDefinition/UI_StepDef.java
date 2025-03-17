package StepDefinition;

import Pages.appPage;
import Utility.SeleniumUtils;
import io.cucumber.java.en.*;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.HashMap;
import java.util.List;


public class UI_StepDef {
    WebDriver driver;

    @Given("I open the CoinMarketCap homepage")
    public void openHomePage() {
        //System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://coinmarketcap.com/");
        driver.manage().window().maximize();
        //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @When("I select {string} dropdown with value {string}")
    public void selectRows(String dropdown, String value) throws InterruptedException {
        appPage obj = new appPage(driver);
       // WebElement showRows = SeleniumUtils.getElement(driver,By.xpath("//span[.='Show rows']"));
        //SeleniumUtils.jsClick(driver, showRows);
        obj.selectRows(value);
        /*-WebElement rowsDropdown = driver.findElement(By.xpath("//span[.='Show rows']/following-sibling::div"));
        SeleniumUtils.jsClick(driver, rowsDropdown);
        WebElement valueRows = driver.findElement(By.xpath("//button[.='" + value + "']"));
        SeleniumUtils.jsClick(driver, valueRows);*/

    }

    @Then("I verify that {int} rows are displayed")
    public void verifyRowsDisplayed(int expectedRows) throws InterruptedException {

        Thread.sleep(3000);
        SeleniumUtils.waitForVisible(driver, By.xpath("//table[contains(@class,'cmc-table')]//tbody/tr"));
        List<WebElement> rowsList = driver.findElements(By.xpath("//table[contains(@class,'cmc-table')]//tbody/tr"));
        Assert.assertEquals(expectedRows, rowsList.size());
        driver.close();
    }

    @When("I click on the {string} button")
    public void clickFiltersButton(String button) {
        WebElement filtersButton = driver.findElement(By.xpath("(//button[.='Filters'])[2]"));
        SeleniumUtils.jsClick(driver, filtersButton);
    }

    @When("I apply filters with {string} = {string} and {string} = {string}")
    public void applyFilters(String filterType1, String filterValue1, String filterType2, String filterValue2) {


        SeleniumUtils.waitForVisible(driver, By.xpath("//button[normalize-space()='+ Add Filter']"));
        WebElement addFilter = driver.findElement(By.xpath("//button[normalize-space()='+ Add Filter']"));
        SeleniumUtils.jsClick(driver, addFilter);


        WebElement filter1 = driver.findElement(By.xpath("//button[.='" + filterType1 + "']"));
        SeleniumUtils.jsClick(driver, filter1);
        WebElement filterVal1 = driver.findElement(By.xpath("//button[.='" + filterValue1 + "']"));
        SeleniumUtils.jsClick(driver, filterVal1);

        WebElement apply = driver.findElement(By.xpath("//button[contains(@data-qa-id,'apply')]"));
        SeleniumUtils.jsClick(driver, apply);

        WebElement filter2 = driver.findElement(By.xpath("//button[.='" + filterType2 + "']"));
        SeleniumUtils.jsClick(driver, filter2);
        WebElement filterVal2 = driver.findElement(By.xpath("//button[.='" + filterValue2 + "']"));
        SeleniumUtils.jsClick(driver, filterVal2);

        apply = driver.findElement(By.xpath("//button[contains(@data-qa-id,'apply')]"));
        SeleniumUtils.waitForVisible(driver, By.xpath("//button[contains(@data-qa-id,'apply')]"));
        SeleniumUtils.jsClick(driver, apply);

        WebElement showResult = driver.findElement(By.xpath("//button[.='Show results']"));
        SeleniumUtils.waitForVisible(driver, By.xpath("//button[.='Show results']"));
        SeleniumUtils.jsClick(driver, showResult);


    }

    @Then("I verify the records displayed are correct based on the applied filter")
    public void verifyFilteredRecords() {
        WebElement filterCheck = driver.findElement(By.xpath("//li[contains(.,'2 More Filters')]"));

        List<WebElement> priceList = driver.findElements(By.xpath("//table[contains(@class,'cmc-table')]/tbody/tr/td[4]//span"));
        for (WebElement eachPrice : priceList) {
            double priceValue = Double.parseDouble(eachPrice.getText()
                    .replaceAll("\\$", ""));
            if (!(priceValue > 100 && priceValue < 1000)) {
                Assert.fail(" Price value : " + priceValue + " is not in filter range");
            }
        }


        List<WebElement> marketCaps = driver.findElements(By.xpath("//table[contains(@class,'cmc-table')]/tbody/tr/td[8]//span[contains(@class,'chpohi')]"));
        for (WebElement eachMarketCap : marketCaps) {

            double marketCapValue = Double.parseDouble(eachMarketCap
                    .getAttribute("innerHTML")
                    .replaceAll("\\$", "")
                    .replaceAll("B", ""));
            if (!(marketCapValue > 1 && marketCapValue < 10)) {
                Assert.fail(" Market Cap value : " + marketCapValue + " is not in filter range");
            }
        }

        driver.close();
    }

    @Given("I open the TestAutomation homepage")
    public void iOpenTheTestAutomationHomepage() {

        //System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://testautomationpractice.blogspot.com/");
        driver.manage().window().maximize();
        //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @When("I click on the label")
    public void iClickOnTheLabel() {
        //
        SeleniumUtils.waitForVisible(driver, By.xpath("//div/h2[.='Pagination Web Table']"));
        WebElement label = driver.findElement(By.xpath("//div/h2[.='Pagination Web Table']"));
        SeleniumUtils.click(driver, label);

    }

    @Then("I fetch and navigate to all product and price")
    public void iFetchAndNavigateToAllProductAndPrice() {
        List<WebElement> listNavigation = driver.findElements(By.xpath("//ul[@id='pagination']/li/a"));
        int numOfPages = listNavigation.size();
        int count=0;
        HashMap<String, String> productMap = new HashMap<>();

        for(int i = 1; i<=numOfPages;i++){
            SeleniumUtils.waitForVisible(driver, By.xpath("(//ul[@id='pagination']/li/a)["+i+"]"));
            WebElement  pageCount = driver.findElement(By.xpath("(//ul[@id='pagination']/li/a)["+i+"]"));
            SeleniumUtils.click(driver, pageCount);


            List<WebElement> listProductRows = driver.findElements(By.xpath("//table[@id='productTable']//tbody/tr"));
            int numOfRows = listProductRows.size();
            for(int j =1 ; j<=numOfRows;j++){
                String product, price ;
                product = driver.findElement(By.xpath("(//table[@id='productTable']//tbody/tr)["+j+"]/td[2]")).getText();
                price = driver.findElement(By.xpath("(//table[@id='productTable']//tbody/tr)["+j+"]/td[3]")).getText();

                System.out.println(++count+product + " : "+price);
                productMap.put(product,price);
            }
        }
        productMap.forEach((k,v)-> System.out.println("Product : "+k +" and its price : "+v));
    }
}
