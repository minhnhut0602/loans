package loans.service;


import loans.domain.ServiceRequest;
import loans.repository.LoanEntity;
import loans.repository.LoanRepository;
import loans.repository.RepositoryStatus;
import loans.utils.DateUtils;
import loans.utils.TestEntityGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Calendar;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ValidationServiceTest {

    @Mock
    private LoanRepository loanRepository;

    private ValidationService validationService;
    private TestEntityGenerator generator = new TestEntityGenerator();

    @Before
    public void setUp() {
        validationService = new ValidationService();
    }

    @Test
    public void validateApplicationSpamRequest() {
        when(loanRepository.findByIpAddress(anyString())).thenReturn((List<LoanEntity>) generator.generateLoanEntityList(5));
        StatusMessage statusMessage = validationService.validateApplyRequest(generator.generateValidServiceRequest(), loanRepository);
        assertThat(statusMessage, is(StatusMessage.POSSIBLE_SPAM));
    }

    @Test
    public void validateExistingEntityRequest() {
        when(loanRepository.findByStatus(any(RepositoryStatus.class))).thenReturn(generator.generateLoanEntity(false, RepositoryStatus.ACCEPTED));
        StatusMessage statusMessage = validationService.validateApplyRequest(generator.generateValidServiceRequest(), loanRepository);
        assertThat(statusMessage, is(StatusMessage.ALREADY_IN_PROGRESS));
    }

    @Test
    public void validateFraudRequest_BeforeMidnight() {
        when(loanRepository.findByStatus(any(RepositoryStatus.class))).thenReturn(null);
        ServiceRequest request = generator.generateInvalidServiceRequest(450.0,10);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 0);
        DateUtils.setCalendarInstance(cal);
        StatusMessage statusMessage = validationService.validateApplyRequest(request, loanRepository);
        assertThat(statusMessage, is(StatusMessage.OK));
    }

    @Test
    public void validateFraudRequest_Midnight() {
        when(loanRepository.findByStatus(any(RepositoryStatus.class))).thenReturn(null);
        ServiceRequest request = generator.generateInvalidServiceRequest(450.0,10);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 1);
        DateUtils.setCalendarInstance(cal);
        StatusMessage statusMessage = validationService.validateApplyRequest(request, loanRepository);
        assertThat(statusMessage, is(StatusMessage.POSSIBLE_FRAUD));
    }

    @Test
    public void validateFraudRequest_EightOClock() {
        when(loanRepository.findByStatus(any(RepositoryStatus.class))).thenReturn(null);
        ServiceRequest request = generator.generateInvalidServiceRequest(450.0,10);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 7);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 0);
        DateUtils.setCalendarInstance(cal);
        StatusMessage statusMessage = validationService.validateApplyRequest(request, loanRepository);
        assertThat(statusMessage, is(StatusMessage.POSSIBLE_FRAUD));
    }

    @Test
    public void validateFraudRequest_Ok() {
        when(loanRepository.findByStatus(any(RepositoryStatus.class))).thenReturn(null);
        ServiceRequest request = generator.generateInvalidServiceRequest(450.0,10);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 8);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        DateUtils.setCalendarInstance(cal);
        StatusMessage statusMessage = validationService.validateApplyRequest(request, loanRepository);
        assertThat(statusMessage, is(StatusMessage.OK));
    }

    @Test
    public void validateInvalidAmount_Zero() {
        when(loanRepository.findByStatus(any(RepositoryStatus.class))).thenReturn(null);
        ServiceRequest request = generator.generateInvalidServiceRequest(0.0, 10);
        StatusMessage statusMessage = validationService.validateApplyRequest(request, loanRepository);
        assertThat(statusMessage, is(StatusMessage.INVALID_AMOUNT));
    }

    @Test
    public void validateInvalidAmount_TooMuch() {
        when(loanRepository.findByStatus(any(RepositoryStatus.class))).thenReturn(null);
        ServiceRequest request = generator.generateInvalidServiceRequest(450.1, 10);
        StatusMessage statusMessage = validationService.validateApplyRequest(request, loanRepository);
        assertThat(statusMessage, is(StatusMessage.INVALID_AMOUNT));
    }

    @Test
    public void validateInvalidAmount_Ok() {
        when(loanRepository.findByStatus(any(RepositoryStatus.class))).thenReturn(null);
        ServiceRequest request = generator.generateInvalidServiceRequest(400.0, 10);
        StatusMessage statusMessage = validationService.validateApplyRequest(request, loanRepository);
        assertThat(statusMessage, is(StatusMessage.OK));
    }

    @Test
    public void validateInvalidTerm_Zero() {
        when(loanRepository.findByStatus(any(RepositoryStatus.class))).thenReturn(null);
        ServiceRequest request = generator.generateInvalidServiceRequest(400.0, 0);
        StatusMessage statusMessage = validationService.validateApplyRequest(request, loanRepository);
        assertThat(statusMessage, is(StatusMessage.INVALID_TERM));
    }

    @Test
    public void validateInvalidTerm_TooLong() {
        when(loanRepository.findByStatus(any(RepositoryStatus.class))).thenReturn(null);
        ServiceRequest request = generator.generateInvalidServiceRequest(400.0, 31);
        StatusMessage statusMessage = validationService.validateApplyRequest(request, loanRepository);
        assertThat(statusMessage, is(StatusMessage.INVALID_TERM));
    }

    @Test
    public void validateInvalidTerm_Ok() {
        when(loanRepository.findByStatus(any(RepositoryStatus.class))).thenReturn(null);
        ServiceRequest request = generator.generateInvalidServiceRequest(400.0, 10);
        StatusMessage statusMessage = validationService.validateApplyRequest(request, loanRepository);
        assertThat(statusMessage, is(StatusMessage.OK));
    }

    @Test
    public void validateExtensionSpamRequest() {
        when(loanRepository.findByIpAddress(anyString())).thenReturn((List<LoanEntity>) generator.generateLoanEntityList(5));
        StatusMessage statusMessage = validationService.validateExtendRequest(generator.generateValidServiceRequest(), loanRepository);
        assertThat(statusMessage, is(StatusMessage.POSSIBLE_SPAM));
    }

    @Test
    public void validateExtension_Ok(){
        when(loanRepository.findByStatus(any(RepositoryStatus.class))).thenReturn(generator.generateLoanEntity(false,RepositoryStatus.ACCEPTED));
        StatusMessage statusMessage = validationService.validateExtendRequest(generator.generateValidServiceRequest(), loanRepository);
        assertThat(statusMessage,is(StatusMessage.OK));
    }

    @Test
    public void validateNothingToExtend(){
        when(loanRepository.findByStatus(any(RepositoryStatus.class))).thenReturn(null);
        StatusMessage statusMessage = validationService.validateExtendRequest(generator.generateValidServiceRequest(), loanRepository);
        assertThat(statusMessage,is(StatusMessage.NOTHING_TO_EXTEND));
    }
    @Test
    public void validateAlreadyExtended(){
        when(loanRepository.findByStatus(any(RepositoryStatus.class))).thenReturn(generator.generateLoanEntity(true,RepositoryStatus.ACCEPTED));
        StatusMessage statusMessage = validationService.validateExtendRequest(generator.generateValidServiceRequest(), loanRepository);
        assertThat(statusMessage,is(StatusMessage.ALREADY_EXTENDED));
    }


}
