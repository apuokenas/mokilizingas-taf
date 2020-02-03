package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
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

    public void click(By locator) {
        find(locator, "my-iframe").click();
    }

    // Reserved for e.mokilizingas.lt
    public WebElement find(By locator) {
        switchToDefault();
        return driver.findElement(locator);
    }

    public WebElement find(By locator, String frame) {
        switchTo(frame);
        return driver.findElement(locator);
    }

    public String getTextOf(By locator) {
        return find(locator, "my-iframe").getText();
    }

    public Boolean isDisplayed(By locator) {
        try {
            return find(locator, "my-iframe").isDisplayed();
        } catch (org.openqa.selenium.NoSuchElementException exception) {
            return false;
        }
    }

    // Reserved for e.mokilizingas.lt
    public Boolean isDisplayed(By locator, Integer timeout) {
        switchToDefault();
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (org.openqa.selenium.TimeoutException exception) {
            return false;
        }
        return true;
    }

    public void select(By locator, String option) {
        Select selectList = new Select(find(locator, "my-iframe"));
        selectList.selectByVisibleText(option);
        assertThat(selectList.getFirstSelectedOption().getText(), is(equalTo(option)));
    }

    public void switchToDefault() {
        driver.switchTo().defaultContent();
    }

    public void switchTo(String context) {
        driver.switchTo().frame(context);
    }

    public void type(By locator, String inputText) {
        find(locator, "my-iframe").sendKeys(inputText);
    }

}
