package loans.service;

import loans.domain.ServiceRequest;
import loans.domain.ServiceResponse;

public interface LoanApplicationService {


    ServiceResponse apply(ServiceRequest request);

    ServiceResponse extend(ServiceRequest request);

    ServiceResponse getHistory();
}
