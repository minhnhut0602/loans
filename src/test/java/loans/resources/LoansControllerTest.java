package loans.resources;

import loans.controller.LoansController;
import loans.domain.ServiceRequest;
import loans.domain.ServiceResponse;
import loans.service.LoanApplicationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class LoansControllerTest {

    @Mock
    private LoanApplicationService loanApplicationService;
    private LoansController loansController;

    @Before
    public void setUp(){
        loansController =new LoansController(loanApplicationService);
    }

    @Test
    public void shouldApplyLoan()throws Exception{
        final ServiceResponse savedLoanServiceResponse = stubServiceToReturnStoredEntity();
        ServiceResponse serviceResponse = loansController.apply("FakeAmount", "FakeTerm");

        verify(loanApplicationService,times(1)).apply(any(ServiceRequest.class));
        assertEquals("Returned entity should come from the service", savedLoanServiceResponse, serviceResponse);
    }
    private ServiceResponse stubServiceToReturnStoredEntity() {
        final ServiceResponse response = new ServiceResponse.ServiceResponseBuilder().build();
        when(loanApplicationService.apply(any(ServiceRequest.class))).thenReturn(response);
        return response;
    }
}
