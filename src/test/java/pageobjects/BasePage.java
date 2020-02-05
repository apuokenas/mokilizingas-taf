package pageobjects;

import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static tests.Config.baseUrl;

/**
 * @author M. Tumėnas
 * @since 2020-02-02
 */
public class BasePage {

    private WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Clicks on a web element which is referred to by a locator argument.
     *
     * @param locator a reference to the web element which is about to be single left-clicked
     */
    public void click(By locator) {
        find(locator).click();
    }

    /**
     * Returns a web element rendered on a web page as a node within DOM based on a locator argument passed.
     *
     * @param locator a reference to the web element to be found
     * @return the web element found based on the locator argument
     */
    public WebElement find(By locator) {
        return driver.findElement(locator);
    }

    /**
     * Returns a string value for a locator argument representing an installment amount if the installment amount is
     * required or a null otherwise.
     *
     * @param installmentAmountRequired true if the installment amount is required or false otherwise
     * @param locator                   a reference to the web element which has text to be extracted from
     * @return either a null or a string value representing the installment amount for a locator argument of the
     * installment amount
     */
    public String getInstallmentAmount(Boolean installmentAmountRequired, By locator) {
        String installmentAmount = null;
        if (installmentAmountRequired) installmentAmount = getTextOf(locator);
        return installmentAmount;
    }

    /**
     * Returns text contents of a tag represented by a web element which in turn is referred to by a locator argument.
     *
     * @param locator a reference to the web element which has intrinsic text contents
     * @return text contents of the web element referred to by the locator argument
     */
    public String getTextOf(By locator) {
        return find(locator).getText();
    }

    /**
     * Returns a boolean value (true/false) of visibility status of a web element referred to by a locator argument.
     *
     * @param locator a reference to the web element which display status is under investigation
     * @return true if the web element referred to by the locator argument was displayed or false if it was not
     * displayed
     */
    public Boolean isDisplayed(By locator) {
        try {
            return find(locator).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Returns a boolean value (true/false) of visibility status of a web element referred to by a locator argument
     * given some time specified in seconds.
     *
     * @param locator a reference to the web element whose display status is under investigation
     * @param timeout time in seconds to wait for the web element referred to by the locator argument to become visible
     * @return true if the web element referred to by the locator argument was displayed or false if it was not
     * displayed during timeout period
     */
    public Boolean isDisplayed(By locator, Integer timeout) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }

    /**
     * Selects a validOption argument only when it is a possible value to choose from all the options available
     * within a drop-down list field.
     *
     * @param dropdownListLocator a web element representing the drop-down list field
     * @param optionsLocator      a web element referring to all the drop-down options available for the
     *                            dropdownListLocator argument
     * @param validOption         a new valid option value to be selected from a set of options defined by the
     *                            optionsLocator argument
     */
    public void selectValid(By dropdownListLocator, By optionsLocator, String validOption) {
        WebElement dropdownList = find(dropdownListLocator);
        List<WebElement> options = dropdownList.findElements(optionsLocator);
        for (WebElement option : options) if (option.getText().equals(validOption)) option.click();
    }

    /**
     * Selects a non null invalidOption argument which is a newly introduced value to choose from all the options
     * available within a drop-down list field.
     * <p>
     * The addition of this new option is done by altering the first present option via modifying its value attribute
     * to the invalidOption argument with its last 5 chars (typically, that would be " mėn.") removed so that it
     * wouldn't differ structurally from other options and then finally replacing the associated tag's inner content.
     *
     * @param dropdownListLocator a web element representing the drop-down list field
     * @param optionsLocator      a web element referring to all the drop-down options available for the
     *                            dropdownListLocator argument
     * @param invalidOption       a new invalid option value to be selected from a set of options defined by the
     *                            optionsLocator argument
     */
    public void selectInvalid(By dropdownListLocator, By optionsLocator, @NotNull String invalidOption) {
        WebElement dropdownList = find(dropdownListLocator);
        List<WebElement> options = dropdownList.findElements(optionsLocator);
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        // Change a value attribute of the first option tag:
        jse.executeScript(
                "arguments[0].value='" + invalidOption.substring(0, invalidOption.length() - 5) + "';",
                options.get(0)
        );
        // Change text of the first option tag:
        jse.executeScript("arguments[0].innerHTML='" + invalidOption + "';", options.get(0));
        // Select the first option:
        options.get(0).click();
    }

    /**
     * Since neither clear() function nor hitting CTRL+A does not work, enters an inputText argument into a locator
     * argument using an alternative precautious strategy by firstly selecting all the potentially preexisting text in
     * the field (just to be sure that the new string value won't be appended to the original value of that text input
     * field) by hitting HOME, then SHIFT+END keys together and finally sending the new char sequence in an ordinary way.
     *
     * @param locator   a reference to the web element representing the text input field
     * @param inputText any string representing a new value for the web element referred to by the locator argument
     */
    public void type(By locator, String inputText) {
        find(locator).sendKeys(Keys.HOME, Keys.chord(Keys.SHIFT, Keys.END), inputText);
    }

    /**
     * Navigates to a given url either by simply entering this argument (in case it is an absolute URL which was
     * provided) into a navigation bar or appending it to a predefined base URL (in case it is a relative path of
     * AUT/SUT which was given) and then passing this newly formed absolute URL into a controlled web browser.
     *
     * @param url an absolute or relative URL of AUT/SUT
     */
    public void visit(String url) {
        if (url.contains("http")) driver.get(url);
        else driver.get(baseUrl + url);
    }

}
