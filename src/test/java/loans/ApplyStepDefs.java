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

    @Given("^My ip address is \\\"([^\\\"]*)\\\"$")
    public void My_ip_address_is(String ipAddress) {
        request.setLocalAddr(ipAddress);
    }

    @When("^I apply from with amount (\\d+) and with term (\\d+)$")
    public void I_apply_from_with_amount_and_with_term(Double amount, Integer term) {
        response = controller.apply(request, amount, term);

    }

    @Then("^I expect to receive a response \\\"([^\\\"]*)\\\"$")
    public void I_expect_to_receive_a_response(String result) {
        assertEquals(result, response.getMessage());
    }
}
