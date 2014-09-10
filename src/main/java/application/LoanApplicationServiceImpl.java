package application;

import resources.LoanApplicationService;
import resources.ServiceRequest;
import resources.ServiceResponse;

public class LoanApplicationServiceImpl implements LoanApplicationService {

    ValidationService validationService = new ValidationService();

    @Override
    public ServiceResponse apply(ServiceRequest request) {
        String message = validationService.validateApplyRequest(request);
        return new ServiceResponse.ServiceResponseBuilder()
                .withMessage(message)
                .build();
    }

    @Override
    public ServiceResponse extend(ServiceRequest request) {
        String message = validationService.validateExtendRequest();
        return new ServiceResponse.ServiceResponseBuilder()
                .withMessage(message)
                .build();
    }

    @Override
    public ServiceResponse getHistory() {
        return new ServiceResponse.ServiceResponseBuilder()
                .withStatus("OK")
                .withMessage("Performed getHistory")
                .build();
    }
}
