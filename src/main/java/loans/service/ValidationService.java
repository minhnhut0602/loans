package loans.service;

import loans.domain.ServiceRequest;
import loans.repository.LoanEntity;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ValidationService {

    private static final Integer POSSIBLE_MAX_AMOUNT = 450;

    public ValidationStatus validateApplyRequest(ServiceRequest request, List<LoanEntity> existingEntriesForThisIp) {
        if (isPossibleSpam(existingEntriesForThisIp)) {
            return ValidationStatus.POSSIBLE_SPAM;
        }
        if (isPossibleFraud(request)) {
           return ValidationStatus.POSSIBLE_FRAUD;
        }

        Integer amount = request.getAmount();
        Integer term = request.getTerm();

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
            if (occurencies >= 3) {
                return true;
            }
            if (loanEntity.getApplicationDate().after(cal.getTime())) {
                occurencies++;
            }
        }
        return false;
    }

    public ValidationStatus validateExtendRequest() {
        return null;  //TODO
    }
}
