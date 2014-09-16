package loans.service;

import loans.domain.ServiceRequest;
import loans.domain.ServiceResponse;
import loans.repository.LoanEntity;
import loans.repository.LoanRepository;
import loans.repository.RepositoryStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.isNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoanApplicationServiceImplTest {

    @Mock
    private LoanRepository loanRepository;
    @Mock
    private ValidationService validationService;

    private LoanApplicationService loanApplicationService;

    @Before
    public void setUp() {
        loanApplicationService = new LoanApplicationServiceImpl(loanRepository,validationService);
    }

    @Test
    public void applicationSuccessful() {
        when(validationService.validateApplyRequest(any(ServiceRequest.class), any(LoanRepository.class))).thenReturn(StatusMessage.OK);
        ServiceResponse serviceResponse = loanApplicationService.apply(generateValidServiceRequest());
        assertThat(serviceResponse.getStatusMessage(), is(StatusMessage.OK.getValue()));
    }

    @Test
    public void applicationDeclined() {
        when(validationService.validateApplyRequest(any(ServiceRequest.class), any(LoanRepository.class))).thenReturn(StatusMessage.INVALID_AMOUNT);
        ServiceResponse serviceResponse=loanApplicationService.apply(generateValidServiceRequest());
        assertThat(serviceResponse.getStatusMessage(), not(StatusMessage.OK.getValue()));
    }

    @Test
    public void extensionSuccessful(){
        when(validationService.validateExtendRequest(any(ServiceRequest.class), any(LoanRepository.class))).thenReturn(StatusMessage.OK);
        when(loanRepository.findByStatus(any(RepositoryStatus.class))).thenReturn(generateLoanEntity(false,RepositoryStatus.ACCEPTED));
        ServiceResponse serviceResponse = loanApplicationService.extend(generateValidServiceRequest());
        assertThat(serviceResponse.getStatusMessage(), is(StatusMessage.OK.getValue()));
    }
    @Test
    public void extensionDeclined(){
        when(validationService.validateExtendRequest(any(ServiceRequest.class), any(LoanRepository.class))).thenReturn(StatusMessage.POSSIBLE_SPAM);
        ServiceResponse serviceResponse = loanApplicationService.extend(generateValidServiceRequest());
        assertThat(serviceResponse.getStatusMessage(), not(StatusMessage.OK.getValue()));
    }

    private LoanEntity generateLoanEntity(boolean extended, RepositoryStatus repositoryStatus) {
        return   new LoanEntity.LoanEntityBuilder()
                .withIpAddress("192.168.1.1")
                .withAmount(30.0)
                .withTerm(30)
                .withExtended(extended)
                .withStatus(repositoryStatus).build();
    }

    private ServiceRequest generateValidServiceRequest() {
        return new ServiceRequest.ServiceRequestBuilder()
                .withIpAddress("192.168.1.2")
                .withAmount(30.0)
                .withTerm(30)
                .build();
    }
}
