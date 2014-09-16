package loans;

import cucumber.api.java.en.And;
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

    @Given("^application is performed from ip \\\"([^\\\"]*)\\\"$")
    public void application_is_performed_from_ip(String ipAddress) {
        request.setRemoteAddr(ipAddress);
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

    @And("^user tries to perform an extension$")
    public void user_tries_to_perform_an_extension(){
        response=controller.extend(request);
    }

    @When("^user requests for history$")
    public void user_requests_for_history() throws Throwable{
        response=controller.getHistory();
    }
}
