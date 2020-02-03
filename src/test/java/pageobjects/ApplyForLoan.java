package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertTrue;

public class ApplyForLoan extends BasePage {

    By loanFormLocator = By.id("moki-banner-popup-paskola");
    By loanAmountLocator = By.xpath("//input[@class='select_suma']"); // Paskolos suma
    By termLocator = By.xpath("//select[@class='select_menuo']"); // Laikotarpis
    By nameLocator = By.name("mokiban-vardas"); // Vardas
    By surnameLocator = By.name("mokiban-pavarde"); // Pavardė
    By ssnLocator = By.name("mokiban-asmens_kodas"); // Asmens kodas
    By phoneNoLocator = By.name("mokiban-telefonas"); // Telefono nr.
    By emailLocator = By.name("mokiban-pastas"); // El. paštas
    By consentToPrivacyPolicyAndRulesCheckbox = By.id("duomenys"); // Patvirtinu, kad susipažinau ir sutinku su AB „Mokilizingas“ Privatumo politika ir Taisyklėmis.
    By consentToMarketingCheckbox = By.id("susisiektu"); // Sutinku, kad AB „Mokilizingas” su manimi susisiektų aukščiau pateiktais kontaktiniais duomenimis siekiant pasiūlyti savo ir partnerių paslaugas bei produktus. Šis sutikimas galioja 1 metus arba kol bus atšauktas
    By submitButton = By.xpath("//a[@class='popup-pildyti']"); // Tęsti >
    By loaningSystemWizardLocator = By.id(""); //TODO: Resolve a typical web element of the "Registracija - E Paskola - Mokilizingas" page when the loaning system goes up
    By failureMessageLocator = By.id("process_closed");
    By installmentAmountLocator = By.xpath("//span[@class='select_imoka']");

    public ApplyForLoan(WebDriver driver) {
        super(driver);
        visit("/paskola");
        assertTrue("The loan form/calculator is not present",
                isDisplayed(loanFormLocator));
    }

    public void as(String name, String surname, String ssn, String phoneNo, String email) {
        type(nameLocator, name);
        type(surnameLocator, surname);
        type(ssnLocator, ssn);
        type(phoneNoLocator, phoneNo);
        type(emailLocator, email);
    }

    public Boolean failureMessagePresent() {
        return isDisplayed(failureMessageLocator, 10);
    }

    public void giveConsents(Boolean regulatoryConsentGiven, Boolean marketingConsentGiven) {
        if (regulatoryConsentGiven) {
            click(consentToPrivacyPolicyAndRulesCheckbox);
        }
        if (marketingConsentGiven) {
            click(consentToMarketingCheckbox);
        }
    }

    public Boolean installmentAmountPresent() {
        return isDisplayed(installmentAmountLocator);
    }

    public Boolean loaningSystemLoaded() {
        return isDisplayed(loaningSystemWizardLocator, 10);
    }

    public void ofAmountAndTerm(String loanAmount, String term) {
        type(loanAmountLocator, loanAmount);
        select(termLocator, term);
    }

    public String ofAmountAndTerm(String loanAmount, String term, Boolean installmentAmountRequired) {
        type(loanAmountLocator, loanAmount);
        select(termLocator, term);
        String installmentAmount = null;
        if (installmentAmountRequired) {
            installmentAmount = getTextOf(installmentAmountLocator);
        }
        switchToDefault();
        return installmentAmount;
    }

    public void submit() {
        click(submitButton);
    }

}
