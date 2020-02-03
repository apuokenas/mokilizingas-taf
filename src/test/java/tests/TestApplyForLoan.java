package tests;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import pageobjects.ApplyForLoan;
import tests.groups.Deep;
import tests.groups.Shallow;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
        assertTrue("Installment amount not present",
                applyForLoan.installmentAmountPresent());
        assertEquals("Installment amount different than expected",
                actualInstallmentAmount, expectedInstallmentAmount);
    }

    @Test
    @Category(Shallow.class)
    public void checkIfMonthlyInstallmentCalculatedForMaxAmountAndMinTerm() {
        String actualInstallmentAmount = applyForLoan.ofAmountAndTerm("15000", "6 mėn.", true);
        String expectedInstallmentAmount = "2649.42 EUR";
        assertTrue("Installment amount not present",
                applyForLoan.installmentAmountPresent());
        assertEquals("Installment amount different than expected",
                actualInstallmentAmount, expectedInstallmentAmount);
    }

    @Test
    @Category(Shallow.class)
    public void checkIfMonthlyInstallmentCalculatedForInvalidMinAmountAndMinTerm() {
        String actualInstallmentAmount = applyForLoan.ofAmountAndTerm("499.99", "6 mėn.", true);
        String expectedInstallmentAmount = "88.66 EUR"; // loanAmount -> "500"
        assertTrue("Installment amount not present",
                applyForLoan.installmentAmountPresent());
        assertEquals("Installment amount different than expected",
                actualInstallmentAmount, expectedInstallmentAmount);
    }

    @Test
    @Category(Shallow.class)
    public void checkIfMonthlyInstallmentCalculatedForInvalidMaxAmountAndMinTerm() {
        String actualInstallmentAmount = applyForLoan.ofAmountAndTerm("15000.0000000001", "72 mėn.", true);
        String expectedInstallmentAmount = "332.16 EUR"; // loanAmount -> "15000"
        assertTrue("Installment amount not present",
                applyForLoan.installmentAmountPresent());
        assertEquals("Installment amount different than expected",
                actualInstallmentAmount, expectedInstallmentAmount);
    }

    @Test
    @Category(Deep.class)
    public void checkIfAppliedWithMarketingConsent() {
        applyForLoan.ofAmountAndTerm("1500", "30 mėn.");
        applyForLoan.as("Vardas", "Pavardėnas", "36312311234", "860012345", "conspiratorial.email@gmail.com");
        applyForLoan.giveConsents(true, true);
        applyForLoan.submit();
        assertTrue("process_closed payment_message not present",
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
        assertTrue("process_closed payment_message not present",
                applyForLoan.failureMessagePresent());
    }

    @Test
    @Category(Shallow.class)
    public void checkIfNotAppliedWhenRequiredFieldsLeftUnpopulated() {
        applyForLoan.ofAmountAndTerm("", "");
        applyForLoan.as("", "", "", "", "");
        applyForLoan.giveConsents(false, false);
        applyForLoan.submit();
        assertTrue("process_closed payment_message not present",
                applyForLoan.failureMessagePresent());
    }

    //TODO: Implement more happy path, sad path and edge case scenarios once the loaning system goes up

}
