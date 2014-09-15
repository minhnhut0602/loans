package loans.service;

import loans.domain.ServiceRequest;
import loans.domain.ServiceResponse;
import loans.repository.LoanEntity;
import loans.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;

import static loans.repository.ApplicationStatus.ACCEPTED;
import static loans.repository.ApplicationStatus.DECLINED;
import static loans.repository.ApplicationStatus.EXTENSION;
import static loans.repository.LoanEntity.LoanEntityBuilder;

@Service
public class LoanApplicationServiceImpl implements LoanApplicationService {

    private final LoanRepository repository;
    private final ValidationService validationService = new ValidationService();

    @Autowired
    public LoanApplicationServiceImpl(final LoanRepository repository) {
        this.repository = repository;
    }

    @Override
    public ServiceResponse apply(ServiceRequest request) {
        ValidationStatus status = validationService.validateApplyRequest(request, repository);
        String resultMessage;
        if (status.equals(ValidationStatus.OK)) {
            LoanEntity loanEntity = new LoanEntityBuilder()
                    .withAmount(request.getAmount())
                    .withStatus(ACCEPTED)
                    .withTerm(request.getTerm())
                    .withIpAddress(request.getIpAddress())
                    .build();
            resultMessage = save(loanEntity);
        } else {
            LoanEntity loanEntity = new LoanEntityBuilder()
                    .withAmount(request.getAmount())
                    .withStatus(DECLINED)
                    .withTerm(request.getTerm())
                    .withIpAddress(request.getIpAddress())
                    .build();
            save(loanEntity);
            resultMessage = status.getValue();
        }
        return new ServiceResponse.ServiceResponseBuilder()
                .withMessage(resultMessage)
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
        LoanEntity loanEntity = repository.findByStatus(ACCEPTED);
        String resultMessage;
        if (loanEntity.isExtended()) {
            resultMessage = "The loan is already extended";
        } else {
            loanEntity.setExtended(true);
            save(loanEntity);

            LoanEntity extension = new LoanEntityBuilder()
                    .withAmount(Double.valueOf(loanEntity.getAmount()) * 1.5)
                    .withTerm(Integer.valueOf(loanEntity.getTerm() + 7))
                    .withStatus(EXTENSION)
                    .withIpAddress(request.getIpAddress())
                    .build();
            resultMessage = save(extension);
        }
        return new ServiceResponse.ServiceResponseBuilder()
                .withMessage(resultMessage)
                .build();
    }

    @Override
    public ServiceResponse getHistory() {
        Iterable<LoanEntity> loanEntities = repository.findAll();
        return new ServiceResponse.ServiceResponseBuilder()
                .withHistoryItems(loanEntities)
                .withMessage("Loan history")
                .build();
    }
}
