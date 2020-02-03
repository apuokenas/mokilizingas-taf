package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.junit.Assert.assertTrue;

public class ApplyForLoan extends BasePage {

    By consentToRegulatoryCheckbox = By.id("duomenys"); // Patvirtinu, kad susipažinau ir sutinku su AB „Mokilizingas“ Privatumo politika ir Taisyklėmis.
    By consentToMarketingCheckbox = By.id("susisiektu"); // Sutinku, kad AB „Mokilizingas” su manimi susisiektų aukščiau pateiktais kontaktiniais duomenimis siekiant pasiūlyti savo ir partnerių paslaugas bei produktus. Šis sutikimas galioja 1 metus arba kol bus atšauktas
    By emailLocator = By.name("mokiban-pastas"); // El. paštas
    By errorMessageForEmailLocator = By.className(".error.error_email");
    By errorMessageForNameLocator = By.className(".error.error_name");
    By errorMessageForPhoneNoLocator = By.className(".error.error_phone");
    By errorMessageForRegulatoryLocator = By.className(".error.error_privacy");
    By errorMessageForSsnLocator = By.className(".error.error_code");
    By errorMessageForSurnameLocator = By.className(".error.error_surname");
    By failureMessageLocator = By.id("process_closed");
    By installmentAmountLocator = By.xpath("//span[@class='select_imoka']");
    By loanAmountLocator = By.xpath("//input[@class='select_suma']"); // Paskolos suma
    By loaningSystemWizardLocator = By.id(""); //TODO: Resolve a typical web element of the "Registracija - E Paskola - Mokilizingas" page when the loaning system goes up
    By loanFormLocator = By.xpath("//iframe[@class]");
    By nameLocator = By.name("mokiban-vardas"); // Vardas
    By phoneNoLocator = By.name("mokiban-telefonas"); // Telefono nr.
    By surnameLocator = By.name("mokiban-pavarde"); // Pavardė
    By ssnLocator = By.name("mokiban-asmens_kodas"); // Asmens kodas
    By submitButton = By.xpath("//a[@class='popup-pildyti']"); // Tęsti >
    By termDropdownListLocator = By.xpath("//select[@class]"); // Laikotarpis
    By termOptionsLocator = By.xpath("//select[@class]/option");

    public ApplyForLoan(WebDriver driver) {
        super(driver);
        visit("/paskola");
        assertTrue("The loan form/calculator is not present",
                isDisplayed(loanFormLocator));
        WebElement context = driver.findElement(loanFormLocator);
        driver.switchTo().frame(context);
    }

    public void as(String name, String surname, String ssn, String phoneNo, String email) {
        type(nameLocator, name);
        type(surnameLocator, surname);
        type(ssnLocator, ssn);
        type(phoneNoLocator, phoneNo);
        type(emailLocator, email);
    }

    public Boolean errorMessageForEmailPresent() {
        return isDisplayed(errorMessageForEmailLocator);
    }

    public Boolean errorMessageForNamePresent() {
        return isDisplayed(errorMessageForNameLocator);
    }

    public Boolean errorMessageForPhoneNoPresent() {
        return isDisplayed(errorMessageForPhoneNoLocator);
    }

    public Boolean errorMessageForRegulatoryPresent() {
        return isDisplayed(errorMessageForRegulatoryLocator);
    }

    public Boolean errorMessageForSsnPresent() {
        return isDisplayed(errorMessageForSsnLocator);
    }

    public Boolean errorMessageForSurnamePresent() {
        return isDisplayed(errorMessageForSurnameLocator);
    }

    public Boolean failureMessagePresent() {
        return isDisplayed(failureMessageLocator, 10);
    }

    public void giveConsents(Boolean regulatoryConsentGiven, Boolean marketingConsentGiven) {
        if (regulatoryConsentGiven) {
            click(consentToRegulatoryCheckbox);
        }
        if (marketingConsentGiven) {
            click(consentToMarketingCheckbox);
        }
    }

    // Reserved for e.mokilizingas.lt
    public Boolean loaningSystemLoaded() {
        return isDisplayed(loaningSystemWizardLocator, 10);
    }

    public void ofAmountAndTerm(String loanAmount, String term) {
        type(loanAmountLocator, loanAmount);
        select(termDropdownListLocator, termOptionsLocator, term);
    }

    public String ofAmountAndTerm(String loanAmount, String term, Boolean installmentAmountRequired) {
        type(loanAmountLocator, loanAmount);
        select(termDropdownListLocator, termOptionsLocator, term);
        String installmentAmount = null;
        if (installmentAmountRequired) {
            installmentAmount = getTextOf(installmentAmountLocator);
        }
        return installmentAmount;
    }

    public void submit() {
        click(submitButton);
    }

}
