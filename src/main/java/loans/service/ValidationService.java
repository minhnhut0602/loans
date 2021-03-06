package loans.service;

import loans.domain.ServiceRequest;
import loans.repository.LoanEntity;
import loans.repository.LoanRepository;
import loans.utils.DateUtils;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static loans.repository.RepositoryStatus.ACCEPTED;
import static loans.service.StatusMessage.ALREADY_EXTENDED;
import static loans.service.StatusMessage.INVALID_AMOUNT;
import static loans.service.StatusMessage.INVALID_TERM;
import static loans.service.StatusMessage.OK;
import static loans.service.StatusMessage.POSSIBLE_FRAUD;
import static loans.service.StatusMessage.POSSIBLE_SPAM;

@Service
public class ValidationService {

    private static final Double POSSIBLE_MAX_AMOUNT = 450.0;
    private static final Integer POSSIBLE_MAX_TERM = 30;
    public static final int MAX_APPLICATIONS_PER_DAY = 3;

    public StatusMessage validateApplyRequest(ServiceRequest request, LoanRepository repository) {
        if (isPossibleSpam(repository.findByIpAddress(request.getIpAddress()))) {
            return POSSIBLE_SPAM;
        }
        if (repository.findByStatus(ACCEPTED) != null) {
            return StatusMessage.ALREADY_IN_PROGRESS;
        }
        if (isPossibleFraud(request)) {
            return POSSIBLE_FRAUD;
        }
        if (POSSIBLE_MAX_AMOUNT < request.getAmount() || request.getAmount() <= 0) {
            return INVALID_AMOUNT;
        }
        if (POSSIBLE_MAX_TERM < request.getTerm() || request.getTerm() <= 0) {
            return INVALID_TERM;
        }
        return StatusMessage.OK;
    }

    private boolean isPossibleFraud(ServiceRequest request) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date minTime = cal.getTime();
        cal.set(Calendar.HOUR_OF_DAY, 8);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date maxTime = cal.getTime();

        Date currentDate = DateUtils.getCalendarInstance().getTime();

        return POSSIBLE_MAX_AMOUNT.equals(request.getAmount()) && currentDate.after(minTime) && currentDate.before(maxTime);
    }

    private boolean isPossibleSpam(List<LoanEntity> existingEntriesForThisIp) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        int occurences = 0;
        for (LoanEntity loanEntity : existingEntriesForThisIp) {
            if (loanEntity.getApplicationDate().after(cal.getTime())) {
                occurences++;
            }
            if (occurences >= MAX_APPLICATIONS_PER_DAY) {
                return true;
            }
        }
        return false;
    }

    public StatusMessage validateExtendRequest(ServiceRequest request, LoanRepository repository) {
        if (isPossibleSpam(repository.findByIpAddress(request.getIpAddress()))) {
            return POSSIBLE_SPAM;
        }
        if (repository.findByStatus(ACCEPTED) == null) {
            return StatusMessage.NOTHING_TO_EXTEND;
        }
        if (repository.findByStatus(ACCEPTED).isExtended()) {
            return ALREADY_EXTENDED;
        }
        return OK;
    }
}
