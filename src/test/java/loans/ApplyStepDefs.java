package loans;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import loans.controller.LoansController;
import loans.domain.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.Assert.assertEquals;

@ContextConfiguration(classes = Application.class)
public class ApplyStepDefs {

    @Autowired
    private LoansController controller;
    private ServiceResponse response;
    private MockHttpServletRequest request = new MockHttpServletRequest();

    //Scenario: Valid application
    @Given("^application is performed from ip \\\"([^\\\"]*)\\\"$")
    public void application_is_performed_from_ip(String ipAddress) {
        request.setLocalAddr(ipAddress);
    }

    @When("^user applies for a loan with amount (\\d+) and with term (\\d+)$")
    public void user_applies_for_a_loan_with_amount_and_with_term(Double amount, Integer term) {
        response = controller.apply(request, amount, term);
    }

    @Then("^response status message is \\\"([^\\\"]*)\\\"$")
    public void response_status_message_is(String result) {
        assertEquals(result, response.getStatusMessage());
    }

    @When("^user applies (\\d+) times for a loan with amount (\\d+) and with term (\\d+)$")
    public void user_applies_times_for_a_loan_with_amount_and_with_term(int applicationCount, Double amount, Integer term) {
        for (int i = 0; i < applicationCount; i++) {
            response = controller.apply(request, amount, term);
        }
    }

    @Then("^status message from the next response is \\\"([^\\\"]*)\\\"$")
    public void status_message_from_the_next_response_is(String result){
        assertEquals(result, response.getStatusMessage());
    }
}
