package Utility;

import org.openqa.selenium.*;
import org.junit.Assert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import Utility.PropertiesReader;


import java.io.IOException;
import java.time.Duration;

public class SeleniumUtils {
    public static int explicitWaitTime = 10;

    public static void waitForVisible(WebDriver driver, WebElement element){
        try{
            new WebDriverWait(driver, Duration.ofSeconds(explicitWaitTime)).until(ExpectedConditions.visibilityOf(element));

        }catch (Exception e){
            e.printStackTrace();
            System.out.println();
            Assert.fail(e.getMessage());

        }
    }

    public static void waitForVisible(WebDriver driver, By element){
        try{
            Thread.sleep(3000);
            new WebDriverWait(driver, Duration.ofSeconds(explicitWaitTime)).until(ExpectedConditions.presenceOfElementLocated(element));

        }catch (Exception e){
            e.printStackTrace();
            System.out.println();
            Assert.fail(e.getMessage());

        }
    }

    public static void click(WebDriver driver, WebElement element) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(explicitWaitTime)).until(ExpectedConditions.elementToBeClickable(element));
            /*-Thread.sleep(3000);*/
            element.click();

        } catch (Exception e) {
            try {

                ((JavascriptExecutor) driver).executeScript(  "arguments[0].scrollIntoView(true);", element);
                ((JavascriptExecutor) driver).executeScript(  "arguments[0].click();", element);
            } catch (Exception e2) {

                e.printStackTrace();
                System.out.println();
                Assert.fail(e.getMessage());
            }
        }

    }
    public static WebElement getElement(WebDriver driver, By locator) {
        return new WebDriverWait(driver, Duration.ofSeconds(explicitWaitTime))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static String getConfigProperty(String key) throws IOException {

        return new PropertiesReader().loadProperties("src/test/resources/Config.properties").getProperty(key);
    }
    public static void jsClick(WebDriver driver, WebElement element) {

            try {
                new WebDriverWait(driver, Duration.ofSeconds(explicitWaitTime)).until(ExpectedConditions.elementToBeClickable(element));
                Thread.sleep(1000);
                ((JavascriptExecutor) driver).executeScript(  "arguments[0].scrollIntoView(true);", element);
                ((JavascriptExecutor) driver).executeScript(  "arguments[0].click();", element);
            } catch (Exception e2) {

                e2.printStackTrace();
                System.out.println();
                Assert.fail(e2.getMessage());
            }


    }

    public static void enterText(WebDriver driver, WebElement element,String key) {

        try {
            new WebDriverWait(driver, Duration.ofSeconds(explicitWaitTime)).until(ExpectedConditions.visibilityOf(element));

            element.clear();
            element.sendKeys(key);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            Assert.fail(e.getMessage());
        }
    }

    public static void selectText(WebDriver driver, WebElement element,String value) {

        try {
            new WebDriverWait(driver, Duration.ofSeconds(explicitWaitTime)).until(ExpectedConditions.elementToBeClickable(element));
            Select select = new Select(element);
            select.selectByVisibleText(value);


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            Assert.fail(e.getMessage());
        }
    }

}
