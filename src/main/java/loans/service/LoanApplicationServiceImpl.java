package loans.service;

import loans.domain.ServiceRequest;
import loans.domain.ServiceResponse;
import loans.repository.LoanEntity;
import loans.repository.LoanRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;

import static loans.repository.LoanEntity.LoanEntityBuilder;

@Service
public class LoanApplicationServiceImpl implements LoanApplicationService {

    private final LoanRepository repository;
    private final ValidationService validationService = new ValidationService();

    @Inject
    public LoanApplicationServiceImpl(final LoanRepository repository) {
        this.repository = repository;
    }

    @Override
    public ServiceResponse apply(ServiceRequest request) {
        String message = validationService.validateApplyRequest(request);
        LoanEntity loanEntity = new LoanEntityBuilder().withAmount(request.getAmount()).withTerm(request.getTerm()).build();
        LoanEntity save = save(loanEntity);
        return new ServiceResponse.ServiceResponseBuilder()
                .build();
    }

    @Transactional
    private LoanEntity save(LoanEntity loanEntity) {
        return repository.save(loanEntity);
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
