package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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

    public void click(By locator) {
        find(locator).click();
    }

    public WebElement find(By locator) {
        return driver.findElement(locator);
    }

    public String getTextOf(By locator) {
        return find(locator).getText();
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

    public void select(By locator1, By locator2, String desiredOption) {
        WebElement dropdownList = find(locator1);
        List<WebElement> options = dropdownList.findElements(locator2);
        for (WebElement option : options) {
            if (option.getText().equals(desiredOption)) {
                option.click();
            }
        }
    }

    public void type(By locator, String inputText) {
        // Since clear() and CTRL+A does not work, select all the text in the field in a rare way and then send the new sequence normally
        find(locator).sendKeys(Keys.HOME, Keys.chord(Keys.SHIFT, Keys.END), inputText);
    }

    public void visit(String url) {
        if (url.contains("http")) {
            driver.get(url);
        } else {
            driver.get(baseUrl + url);
        }
    }

}
