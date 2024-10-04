package Utility;

import org.openqa.selenium.*;
import org.junit.Assert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SeleniumUtils {
    public static int explicitWaitTime = 10;

    public static void click(WebDriver driver, WebElement element) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(explicitWaitTime)).until(ExpectedConditions.elementToBeClickable(element));
            Thread.sleep(3000);
            element.click();

        } catch (Exception e) {
            try {

                JavascriptExecutor executor = (JavascriptExecutor) driver;
                executor.executeScript("arguments[0].scrollIntoView();", element);
                executor.executeScript("argument[0].click();", element);
            } catch (Exception e2) {

                e.printStackTrace();
                System.out.println();
                Assert.fail(e.getMessage());
            }
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
