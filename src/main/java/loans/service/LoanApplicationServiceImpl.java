package loans.service;

import loans.domain.ServiceRequest;
import loans.domain.ServiceResponse;
import loans.repository.LoanEntity;
import loans.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;

import static loans.repository.LoanEntity.LoanEntityBuilder;
import static loans.repository.RepositoryStatus.ACCEPTED;
import static loans.repository.RepositoryStatus.DECLINED;
import static loans.repository.RepositoryStatus.EXTENSION;
import static loans.service.StatusMessage.OK;

@Service
public class LoanApplicationServiceImpl implements LoanApplicationService {

    private final LoanRepository repository;
    private final ValidationService validationService;

    @Autowired
    public LoanApplicationServiceImpl(final LoanRepository repository, final ValidationService validationService) {
        this.repository = repository;
        this.validationService = validationService;
    }

    @Override
    public ServiceResponse apply(ServiceRequest request) {
        StatusMessage status = validationService.validateApplyRequest(request, repository);
        if (status.equals(OK)) {
            LoanEntity loanEntity = new LoanEntityBuilder()
                    .withAmount(request.getAmount())
                    .withStatus(ACCEPTED)
                    .withTerm(request.getTerm())
                    .withIpAddress(request.getIpAddress())
                    .build();
            status = save(loanEntity);
        } else {
            LoanEntity loanEntity = new LoanEntityBuilder()
                    .withAmount(request.getAmount())
                    .withStatus(DECLINED)
                    .withTerm(request.getTerm())
                    .withIpAddress(request.getIpAddress())
                    .build();
            save(loanEntity);
        }
        return new ServiceResponse.ServiceResponseBuilder()
                .withMessage(status.getValue())
                .build();
    }

    @Override
    public ServiceResponse extend(ServiceRequest request) {
        StatusMessage status = validationService.validateExtendRequest(request, repository);
        if (status.equals(OK)) {
            LoanEntity loanEntity = repository.findByStatus(ACCEPTED);
            loanEntity.setExtended(true);
            save(loanEntity);
            LoanEntity extension = new LoanEntityBuilder()
                    .withAmount(Double.valueOf(loanEntity.getAmount()) * 1.5)
                    .withTerm(Integer.valueOf(loanEntity.getTerm() + 7))
                    .withStatus(EXTENSION)
                    .withIpAddress(request.getIpAddress())
                    .build();
            status = save(extension);
        }
        return new ServiceResponse.ServiceResponseBuilder()
                .withMessage(status.getValue())
                .build();
    }

    @Override
    public ServiceResponse getHistory() {
        Iterable<LoanEntity> loanEntities = repository.findAll();
        StatusMessage status = OK;
        if (((ArrayList) loanEntities).isEmpty()) {
            status = StatusMessage.HISTORY_EMPTY;
        }
        return new ServiceResponse.ServiceResponseBuilder()
                .withHistoryItems(loanEntities)
                .withMessage(status.getValue())
                .build();
    }

    @Transactional
    protected StatusMessage save(LoanEntity loanEntity) {
        try {
            repository.save(loanEntity);
            return OK;
        } catch (Exception e) {
            return StatusMessage.SAVING_ERROR;
        }
    }
}
