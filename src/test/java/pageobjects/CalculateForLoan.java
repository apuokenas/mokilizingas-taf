package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.junit.Assert.assertTrue;

/**
 * @author M. Tumėnas
 * @since 2020-02-02
 */
public class CalculateForLoan extends BasePage {

    By loanCalculatorLocator = By.xpath("//iframe[@class]"); // Form "autobilis.lt PASKOLA"
    By loanAmountLocator = By.xpath("//input[@class='select_suma']"); // Paskolos suma
    By termDropdownListLocator = By.xpath("//select[@class]"); // Laikotarpis
    By termOptionsLocator = By.xpath("//select[@class]/option");
    By installmentAmountLocator = By.xpath("//span[@class='select_imoka']");
    By nameLocator = By.name("mokiban-vardas"); // Vardas
    By surnameLocator = By.name("mokiban-pavarde"); // Pavardė
    By ssnLocator = By.name("mokiban-asmens_kodas"); // Asmens kodas
    By phoneNoLocator = By.name("mokiban-telefonas"); // Telefono nr.
    By emailLocator = By.name("mokiban-pastas"); // El. paštas
    By consentToRegulatoryCheckbox = By.id("duomenys"); // Patvirtinu, kad susipažinau ir sutinku su AB „Mokilizingas“ Privatumo politika ir Taisyklėmis.
    By consentToMarketingCheckbox = By.id("susisiektu"); // Sutinku, kad AB „Mokilizingas” su manimi susisiektų aukščiau pateiktais kontaktiniais duomenimis siekiant pasiūlyti savo ir partnerių paslaugas bei produktus. Šis sutikimas galioja 1 metus arba kol bus atšauktas
    By submitButton = By.xpath("//a[@class='popup-pildyti']"); // Tęsti >
    // Since By.className(".error.error_name") has issues being found the WebDriver:
    By errorMessageForNameLocator = By.xpath("//p[contains(@class, 'error_name')]"); // Užpildykite privalomą lauką
    By errorMessageForSurnameLocator = By.xpath("//p[contains(@class, 'error_surname')]"); // Užpildykite privalomą lauką
    By errorMessageForSsnLocator = By.xpath("//p[contains(@class, 'error_code')]"); // Užpildykite privalomą lauką
    By errorMessageForPhoneNoLocator = By.xpath("//p[contains(@class, 'error_phone')]"); // Užpildykite privalomą lauką
    By errorMessageForEmailLocator = By.xpath("//p[contains(@class, 'error_email')]"); // Užpildykite privalomą lauką
    By errorMessageForRegulatoryLocator = By.xpath("//p[contains(@class, 'error_privacy')]"); // Patvirtinkite, kad susipažinote ir sutinkate su AB „Mokilizingas“ Privatumo politika ir Taisyklėmis.

    public CalculateForLoan(WebDriver driver) {
        super(driver);
        visit("/paskola");
        assertTrue("Autobilis loan calculator is not present",
                isDisplayed(loanCalculatorLocator));
        WebElement context = find(loanCalculatorLocator);
        driver.switchTo().frame(context);
    }

    public void ofAmountAndTerm(String loanAmount, String term) {
        type(loanAmountLocator, loanAmount);
        selectValid(termDropdownListLocator, termOptionsLocator, term);
    }

    public String ofAmountAndTerm(String loanAmount, String validTerm, Boolean installmentAmountRequired) {
        type(loanAmountLocator, loanAmount);
        selectValid(termDropdownListLocator, termOptionsLocator, validTerm);
        return getInstallmentAmount(installmentAmountRequired, installmentAmountLocator);
    }

    public String ofAmountAndInvalidTerm(String loanAmount, String invalidTerm, Boolean installmentAmountRequired) {
        type(loanAmountLocator, loanAmount);
        selectInvalid(termDropdownListLocator, termOptionsLocator, invalidTerm);
        return getInstallmentAmount(installmentAmountRequired, installmentAmountLocator);
    }

    public void as(String name, String surname, String ssn, String phoneNo, String email) {
        type(nameLocator, name);
        type(surnameLocator, surname);
        type(ssnLocator, ssn);
        type(phoneNoLocator, phoneNo);
        type(emailLocator, email);
    }

    public void giveConsents(Boolean regulatoryConsentGiven, Boolean marketingConsentGiven) {
        if (regulatoryConsentGiven) click(consentToRegulatoryCheckbox);
        if (marketingConsentGiven) click(consentToMarketingCheckbox);
    }

    public Boolean errorMessageForNamePresent() {
        return isDisplayed(errorMessageForNameLocator);
    }

    public Boolean errorMessageForSurnamePresent() {
        return isDisplayed(errorMessageForSurnameLocator);
    }

    public Boolean errorMessageForSsnPresent() {
        return isDisplayed(errorMessageForSsnLocator);
    }

    public Boolean errorMessageForPhoneNoPresent() {
        return isDisplayed(errorMessageForPhoneNoLocator);
    }

    public Boolean errorMessageForEmailPresent() {
        return isDisplayed(errorMessageForEmailLocator);
    }

    public Boolean errorMessageForRegulatoryPresent() {
        return isDisplayed(errorMessageForRegulatoryLocator);
    }

    public void submit() {
        click(submitButton);
    }

}
