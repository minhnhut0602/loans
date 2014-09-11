package loans.service;

import loans.domain.ServiceRequest;
import loans.repository.LoanEntity;

import java.util.List;

public class ValidationService {

    public ValidationStatus validateApplyRequest(ServiceRequest request, List<LoanEntity> existingEntriesForThisIp) {
        Integer amount = request.getAmount();
        Integer term = request.getTerm();

        return ValidationStatus.OK;
    }

    public ValidationStatus validateExtendRequest() {
        return null;  //TODO
    }
}
