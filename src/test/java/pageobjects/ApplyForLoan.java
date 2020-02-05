package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author M. Tumėnas
 * @since 2020-02-02
 */
public class ApplyForLoan extends BasePage {

    By wizardGeneralLocator = By.id("myform");
    // Since form[@id='myform'] is not time-specific and displayed always, it is required to make this locator more
    // precise to tailor it to different scenarios (22:00-07:00 vs 07:00-22:00)
    By wizardActiveLocator = By.xpath("//form[contains(., 'Pildoma paraiška: Paskolai automobiliui')]");
    // Remiantis vartojimo kredito įstatymu, vartojimo kredito sutartys nuo 22 iki 7 valandos ryto negali būti sudaromos.
    By wizardInactiveLocator = By.id("process_closed"); //form//div[@id='process_closed']

    public ApplyForLoan(WebDriver driver, Boolean expectingExternalPartyToBeLoaded) {
        super(driver);
        driver.switchTo().defaultContent();
        if (expectingExternalPartyToBeLoaded) {
            assertTrue("Mokilizingas loaning system is not present",
                    isDisplayed(wizardGeneralLocator, 10));
        } else {
            assertFalse("Mokilizingas loaning system is present",
                    isDisplayed(wizardGeneralLocator, 10));
        }
    }

    public Boolean activeWizardPresent() {
        return isDisplayed(wizardActiveLocator, 15);
    }

    public Boolean inactiveWizardPresent() {
        return isDisplayed(wizardInactiveLocator, 15);
    }

}
