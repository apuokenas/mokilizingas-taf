package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertTrue;

public class Loan extends BasePage {

    By loanFormLocator = By.id("moki-banner-popup-paskola");
    By loanAmountLocator = By.cssSelector(".select_suma"); // Paskolos suma
    By termDropdownListLocator = By.cssSelector(".select_menuo"); // Laikotarpis
    By termOptionsLocator = By.cssSelector(".select_menuo");
    By nameLocator = By.name("mokiban-vardas"); // Vardas
    By surnameLocator = By.name("mokiban-pavarde"); // Pavardė
    By ssnLocator = By.name("mokiban-asmens_kodas"); // Asmens kodas
    By phoneNoLocator = By.name("mokiban-telefonas"); // Telefono nr.
    By emailLocator = By.name("mokiban-pastas"); // El. paštas
    By consentToPrivacyPolicyAndRulesCheckbox = By.id("duomenys"); // Patvirtinu, kad susipažinau ir sutinku su AB „Mokilizingas“ Privatumo politika ir Taisyklėmis.
    By consentToMarketingCheckbox = By.id("susisiektu"); // Sutinku, kad AB „Mokilizingas” su manimi susisiektų aukščiau pateiktais kontaktiniais duomenimis siekiant pasiūlyti savo ir partnerių paslaugas bei produktus. Šis sutikimas galioja 1 metus arba kol bus atšauktas
    By submitButton = By.cssSelector(".popup-pildyti"); // Tęsti >
//  By successMessageLocator = By.id(""); //TODO: Resolve a typical web element of the "Registracija - E Paskola - Mokilizingas" page when the loaning system goes up
    By failureMessageLocator = By.id("process_closed");

    public Loan(WebDriver driver) {
        super(driver);
        visit("/paskola");
        assertTrue("The loan form/calculator is not present",
                isDisplayed(loanFormLocator));
    }

    public void applyForAndAgreeToMarketing(String loanAmount, String term, String name, String surname, String ssn, String phoneNo, String email) {
        type(loanAmount, loanAmountLocator);
        select(termDropdownListLocator, termOptionsLocator, term);
        type(name, nameLocator);
        type(surname, surnameLocator);
        type(ssn, ssnLocator);
        type(phoneNo, phoneNoLocator);
        type(email, emailLocator);
        click(consentToPrivacyPolicyAndRulesCheckbox);
        click(consentToMarketingCheckbox);
        click(submitButton);
    }

//    public Boolean successMessagePresent() {
//        isDisplayed(successMessageLocator, 1);
//        return isDisplayed(successMessageLocator);
//    }

    public Boolean failureMessagePresent() {
        isDisplayed(failureMessageLocator, 1);
        return isDisplayed(failureMessageLocator);
    }
}
