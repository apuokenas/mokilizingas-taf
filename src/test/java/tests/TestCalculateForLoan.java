package tests;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import pageobjects.ApplyForLoan;
import pageobjects.CalculateForLoan;
import tests.groups.Deep;
import tests.groups.Shallow;

import static org.junit.Assert.*;

/**
 * @author M. Tumėnas
 * @since 2020-02-02
 */
public class TestCalculateForLoan extends BaseTest {

    private CalculateForLoan calculateForLoan;
    private ApplyForLoan applyForLoan;

    @Before
    public void setUp() {
        calculateForLoan = new CalculateForLoan(driver);
    }

    @Test
    @Category(Shallow.class)
    public void checkMonthlyInstallmentCalculatedForMinAmountAndMaxTerm() {
        String actualInstallmentAmount = calculateForLoan.ofAmountAndTerm("500", "72 mėn.", true);
        String expectedInstallmentAmount = "11.17 EUR";
        assertEquals("Installment amount is different",
                expectedInstallmentAmount, actualInstallmentAmount);
    }

    @Test
    @Category(Shallow.class)
    public void checkMonthlyInstallmentCalculatedForMaxAmountAndMinTerm() {
        String actualInstallmentAmount = calculateForLoan.ofAmountAndTerm("15000", "6 mėn.", true);
        String expectedInstallmentAmount = "2649.42 EUR";
        assertEquals("Installment amount is different",
                expectedInstallmentAmount, actualInstallmentAmount);
    }

    @Test
    @Category(Shallow.class)
    public void checkMonthlyInstallmentCalculatedForInvalidMinAmountAndInvalidTerm() {
        String actualInstallmentAmount = calculateForLoan.ofAmountAndInvalidTerm("499.99", "0 mėn.", true);
        String expectedInstallmentAmount = "11.17 EUR"; // Given loanAmount -> "500" (defaulted to min), invalidTerm -> 72 mėn. (defaulted to max)
        assertEquals("Installment amount is different",
                expectedInstallmentAmount, actualInstallmentAmount);
    }

    @Test
    @Category(Shallow.class)
    public void checkIfMonthlyInstallmentCalculatedForInvalidMaxAmountAndMidTerm() {
        String actualInstallmentAmount = calculateForLoan.ofAmountAndTerm("15000.0000000001", "39 mėn.", true);
        String expectedInstallmentAmount = "508.42 EUR"; // Given loanAmount -> "15000" (defaulted to max)
        assertEquals("Installment amount is different",
                expectedInstallmentAmount, actualInstallmentAmount);
    }

    @Test
    @Category(Deep.class)
    public void checkIfProceededWithMarketingConsent() {
        calculateForLoan.ofAmountAndTerm("1500", "30 mėn.");
        calculateForLoan.as("Vardas", "Pavardėnas", "36312311234", "860012345", "conspiratorial.email@gmail.com");
        calculateForLoan.giveConsents(true, true);
        calculateForLoan.submit();
        applyForLoan = new ApplyForLoan(driver, true);
        assertTrue("Loan application for a car cannot be filled in",
                applyForLoan.activeWizardPresent());
    }

    @Test
    @Category(Deep.class)
    public void checkIfProceededWithoutMarketingConsent() {
        calculateForLoan.ofAmountAndTerm("1500", "30 mėn.");
        calculateForLoan.as("Vardas", "Pavardėnas", "36312311234", "860012345", "conspiratorial.email@gmail.com");
        calculateForLoan.giveConsents(true, false);
        calculateForLoan.submit();
        applyForLoan = new ApplyForLoan(driver, true);
        assertTrue("Loan application for a car cannot be filled in",
                applyForLoan.activeWizardPresent());
    }

    @Test
    @Category(Deep.class)
    public void checkIfCannotApplyOutsideLegalHours() {
        calculateForLoan.ofAmountAndTerm("1500", "30 mėn.");
        calculateForLoan.as("Vardas", "Pavardėnas", "36312311234", "860012345", "conspiratorial.email@gmail.com");
        calculateForLoan.giveConsents(true, true);
        calculateForLoan.submit();
        applyForLoan = new ApplyForLoan(driver, true);
        assertTrue("Loan application for a car can be filled in",
                applyForLoan.inactiveWizardPresent());
    }

    @Test
    @Category(Shallow.class)
    public void checkForErrorsWhenAllFieldsLeftUnpopulated() {
        calculateForLoan.as("", "", "", "", "");
        calculateForLoan.giveConsents(false, false);
        calculateForLoan.submit();
        assertTrue("error_name message not present",
                calculateForLoan.errorMessageForNamePresent());
        assertTrue("error_surname message not present",
                calculateForLoan.errorMessageForSurnamePresent());
        assertTrue("error_code message not present",
                calculateForLoan.errorMessageForSsnPresent());
        assertTrue("error_phone message not present",
                calculateForLoan.errorMessageForPhoneNoPresent());
        assertTrue("error_email message not present",
                calculateForLoan.errorMessageForEmailPresent());
        assertTrue("error_privacy message not present",
                calculateForLoan.errorMessageForRegulatoryPresent());
    }

    @Test
    @Category(Shallow.class)
    public void checkIfNotProceededWhenRequiredFieldsLeftUnpopulated() {
        calculateForLoan.as("", "", "", "", "");
        calculateForLoan.giveConsents(false, true);
        calculateForLoan.submit();
        applyForLoan = new ApplyForLoan(driver, false);
        assertFalse("Loan application for a car can be filled in",
                applyForLoan.activeWizardPresent());
    }

}
