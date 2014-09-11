package loans.service;

import loans.domain.ServiceRequest;
import loans.repository.LoanEntity;
import loans.repository.LoanRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static loans.service.ValidationStatus.INVALID_AMOUNT;
import static loans.service.ValidationStatus.INVALID_TERM;
import static loans.service.ValidationStatus.POSSIBLE_FRAUD;
import static loans.service.ValidationStatus.POSSIBLE_SPAM;

public class ValidationService {

    private static final Integer POSSIBLE_MAX_AMOUNT = 450;
    private static final Integer POSSIBLE_MAX_TERM = 30;
    public static final int MAX_APPLICATIONS_PER_DAY = 3;

    public ValidationStatus validateApplyRequest(ServiceRequest request, LoanRepository repository) {
        if(repository.findByStatus("ACCEPTED")!=null){
            return ValidationStatus.ALREADY_IN_PROGRESS;
        }
        if (isPossibleSpam(repository.findByIpAddress(request.getIpAddress()))) {
            return POSSIBLE_SPAM;
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
        return ValidationStatus.OK;
    }

    private boolean isPossibleFraud(ServiceRequest request) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date todaysMidnight = cal.getTime();
        Calendar cal1 = Calendar.getInstance();
        cal1.set(Calendar.HOUR_OF_DAY, 8);
        cal1.set(Calendar.MINUTE, 0);
        cal1.set(Calendar.SECOND, 0);
        cal1.set(Calendar.MILLISECOND, 0);
        Date eightoclock = cal1.getTime();

        Date currentDate = new Date();

        return POSSIBLE_MAX_AMOUNT.equals(request.getAmount()) && currentDate.after(todaysMidnight) && currentDate.before(eightoclock);
    }

    private boolean isPossibleSpam(List<LoanEntity> existingEntriesForThisIp) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        int occurencies = 0;
        for (LoanEntity loanEntity : existingEntriesForThisIp) {
            if (loanEntity.getApplicationDate().after(cal.getTime())) {
                occurencies++;
            }
            if (occurencies >= MAX_APPLICATIONS_PER_DAY) {
                return true;
            }
        }
        return false;
    }
}
