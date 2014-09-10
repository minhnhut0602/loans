package loans.application;

import loans.service.LoanApplicationServiceImpl;
import loans.service.ValidationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import loans.repository.LoanEntity;
import loans.repository.LoanRepository;
import loans.service.LoanApplicationService;
import loans.domain.ServiceRequest;
import loans.domain.ServiceResponse;

import static junit.framework.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoanApplicationServiceImplTest {

    @Mock
    private LoanRepository loanRepository;

    private LoanApplicationService loanApplicationService;
    private ValidationService validationService;

    @Before
    public void setUp(){
        loanApplicationService = new LoanApplicationServiceImpl(loanRepository);
    }

    @Test
    public void shouldSaveNewEntry(){
        final ServiceResponse responseStub = stubResponseOnSave();
        final ServiceRequest request = new ServiceRequest.ServiceRequestBuilder().withAmount("FakeAmount").withTerm("FakeTerm").build();
        final ServiceResponse response = loanApplicationService.apply(request);
        verify(loanRepository,times(1)).save(any(LoanEntity.class));

        assertNotNull(response);
    }
    private ServiceResponse stubResponseOnSave(){
        ServiceResponse response = new ServiceResponse.ServiceResponseBuilder().build();
        when(loanRepository.save(any(LoanEntity.class))).thenReturn(new LoanEntity.LoanEntityBuilder().withAmount("FakeAmount").withTerm("FakeTerm").build());
        return response;
    }
}
