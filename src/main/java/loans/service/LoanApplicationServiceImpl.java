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
        ValidationStatus status = validationService.validateApplyRequest(request, repository.findByIpAddress(request.getIpAddress()));
        String responseMessage = "NOT OKAY";
        if (status.equals(ValidationStatus.OK)) {
            LoanEntity loanEntity = new LoanEntityBuilder()
                    .withAmount(request.getAmount())
                    .withTerm(request.getTerm())
                    .withIpAddress(request.getIpAddress())
                    .build();
            responseMessage = save(loanEntity);
        }
        return new ServiceResponse.ServiceResponseBuilder()
                .withMessage(responseMessage)
                .build();
    }

    @Transactional
    private String save(LoanEntity loanEntity) {
        try {
            repository.save(loanEntity);
            return "Okay";
        } catch (Exception e) {
            return "Some Error occured during saving loan entity";
        }
    }

    @Override
    public ServiceResponse extend(ServiceRequest request) {
        ValidationStatus status = validationService.validateExtendRequest();
        return new ServiceResponse.ServiceResponseBuilder()
                .build();
    }

    @Override
    public ServiceResponse getHistory() {
        return new ServiceResponse.ServiceResponseBuilder()
                .withMessage("Performed getHistory")
                .build();
    }
}
