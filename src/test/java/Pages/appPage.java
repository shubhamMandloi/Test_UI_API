package Pages;

import Utility.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

public class appPage {
    WebDriver driver;

    public appPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }
    @FindBy(xpath = "//span[.='Show rows']")
    WebElement showRows;

    @FindBy(xpath = "//span[.='Show rows']/following-sibling::div")
    WebElement rowsDropdown;
    /*-@FindBy(xpath = "//button[.='" + value + "']")
    WebElement valueRows;*/

    public void selectRows(String value){
        SeleniumUtils.jsClick(driver,showRows);
        SeleniumUtils.jsClick(driver,rowsDropdown);
        SeleniumUtils.jsClick(driver,driver.findElement(By.xpath("//button[.='" + value + "']")));


    }





}
