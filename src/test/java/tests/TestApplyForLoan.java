package tests;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import pageobjects.ApplyForLoan;
import tests.groups.Deep;
import tests.groups.Shallow;

import static org.junit.Assert.*;

public class TestApplyForLoan extends BaseTest {

    private ApplyForLoan applyForLoan;

    @Before
    public void setUp() {
        applyForLoan = new ApplyForLoan(driver);
    }

    @Test
    @Category(Shallow.class)
    public void checkIfMonthlyInstallmentCalculatedForMinAmountAndMaxTerm() {
        String actualInstallmentAmount = applyForLoan.ofAmountAndTerm("500", "72 mėn.", true);
        String expectedInstallmentAmount = "11.17 EUR";
        assertEquals("Installment amount different than expected",
                expectedInstallmentAmount, actualInstallmentAmount);
    }

    @Test
    @Category(Shallow.class)
    public void checkIfMonthlyInstallmentCalculatedForMaxAmountAndMinTerm() {
        String actualInstallmentAmount = applyForLoan.ofAmountAndTerm("15000", "6 mėn.", true);
        String expectedInstallmentAmount = "2649.42 EUR";
        assertEquals("Installment amount different than expected",
                expectedInstallmentAmount, actualInstallmentAmount);
    }

    @Test
    @Category(Shallow.class)
    public void checkIfMonthlyInstallmentCalculatedForInvalidMinAmountAndMinTerm() {
        String actualInstallmentAmount = applyForLoan.ofAmountAndTerm("499.99", "6 mėn.", true);
        String expectedInstallmentAmount = "88.66 EUR"; // loanAmount -> "500"
        assertEquals("Installment amount different than expected",
                expectedInstallmentAmount, actualInstallmentAmount);
    }

    @Test
    @Category(Shallow.class)
    public void checkIfMonthlyInstallmentCalculatedForInvalidMaxAmountAndMinTerm() {
        String actualInstallmentAmount = applyForLoan.ofAmountAndTerm("15000.0000000001", "72 mėn.", true);
        String expectedInstallmentAmount = "332.16 EUR"; // loanAmount -> "15000"
        assertEquals("Installment amount different than expected",
                expectedInstallmentAmount, actualInstallmentAmount);
    }

    @Test
    @Category(Deep.class)
    public void checkIfAppliedWithMarketingConsent() {
        applyForLoan.ofAmountAndTerm("1500", "30 mėn.");
        applyForLoan.as("Vardas", "Pavardėnas", "36312311234", "860012345", "conspiratorial.email@gmail.com");
        applyForLoan.giveConsents(true, true);
        applyForLoan.submit();
        assertFalse("process_closed payment_message present",
                applyForLoan.failureMessagePresent());
//      assertTrue("Loaning system not loaded",
//              applyForLoan.loaningSystemLoaded());
    }

    @Test
    @Category(Deep.class)
    public void checkIfAppliedWithoutMarketingConsent() {
        applyForLoan.ofAmountAndTerm("1500", "30 mėn.");
        applyForLoan.as("Vardas", "Pavardėnas", "36312311234", "860012345", "conspiratorial.email@gmail.com");
        applyForLoan.giveConsents(true, false);
        applyForLoan.submit();
        assertFalse("process_closed payment_message present",
                applyForLoan.failureMessagePresent());
//      assertTrue("Loaning system not loaded",
//              applyForLoan.loaningSystemLoaded());
    }

    @Test
    @Category(Shallow.class)
    public void checkIfNotAppliedWhenRequiredFieldsLeftUnpopulated() {
        applyForLoan.ofAmountAndTerm("", "");
        applyForLoan.as("", "", "", "", "");
        applyForLoan.giveConsents(false, false);
        applyForLoan.submit();
        assertTrue("error_name message not present",
                applyForLoan.errorMessageForNamePresent());
        assertTrue("error_surname message not present",
                applyForLoan.errorMessageForSurnamePresent());
        assertTrue("error_code message not present",
                applyForLoan.errorMessageForSsnPresent());
        assertTrue("error_phone message not present",
                applyForLoan.errorMessageForPhoneNoPresent());
        assertTrue("error_email message not present",
                applyForLoan.errorMessageForEmailPresent());
        assertTrue("error_privacy message not present",
                applyForLoan.errorMessageForRegulatoryPresent());
    }

    //TODO: Implement more happy path, sad path and edge case scenarios once the loaning system goes up

}
