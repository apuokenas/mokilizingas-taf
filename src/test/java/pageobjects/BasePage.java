package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static tests.Config.baseUrl;

public class BasePage {

    private WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    public void visit(String url) {
        if (url.contains("http")) {
            driver.get(url);
        } else {
            driver.get(baseUrl + url);
        }
    }

    public WebElement find(By locator) {
        return driver.findElement(locator);
    }

    public void click(By locator) {
        find(locator).click();
    }

    public void select(By locator1, By locator2, String optionToBeSelected) {
        WebElement dropdownList = driver.findElement(locator1);
        List<WebElement> options = dropdownList.findElements(locator2);
        for (int i = 0; i < options.size(); i++) {
            if (options.get(i).getText().equals(optionToBeSelected)) {
                options.get(i).click();
            }
        }
    }

    public void type(String inputText, By locator) {
        find(locator).sendKeys(inputText);
    }

    public Boolean isDisplayed(By locator) {
        try {
            return find(locator).isDisplayed();
        } catch (org.openqa.selenium.NoSuchElementException exception) {
            return false;
        }
    }

    public Boolean isDisplayed(By locator, Integer timeout) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (org.openqa.selenium.TimeoutException exception) {
            return false;
        }
        return true;
    }

}