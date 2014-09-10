package application;

import org.springframework.stereotype.Service;
import repository.LoanEntity;
import repository.LoanRepository;
import resources.LoanApplicationService;
import resources.ServiceRequest;
import resources.ServiceResponse;

import javax.inject.Inject;

@Service
public class LoanApplicationServiceImpl implements LoanApplicationService {

    private ValidationService validationService = new ValidationService();
    private final LoanRepository repository;

    @Inject
    public LoanApplicationServiceImpl(final LoanRepository repository) {
        this.repository = repository;
    }

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
                .withMessage("Performed getHistory")
                .build();
    }
}
