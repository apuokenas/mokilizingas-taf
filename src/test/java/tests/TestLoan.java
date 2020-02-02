package tests;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import org.junit.experimental.categories.Category;
import pageobjects.Loan;
import tests.groups.Deep;
import tests.groups.Shallow;

public class TestLoan extends BaseTest {

    private Loan loan;

    @Before
    public void setUp() {
        loan = new Loan(driver);
    }

    @Test
    @Category(Deep.class)
    public void succeeded() {
        loan.applyForAndAgreeToMarketing("1500","30 mėn.", "Vardas", "Pavardėnas", "36312311234", "860012345", "conspiratorial.email@gmail.com");
        assertTrue("process_closed payment_message was not present",
                loan.failureMessagePresent());
    }

    //TODO: Implement more happy path, sad path and edge case scenarios

}
