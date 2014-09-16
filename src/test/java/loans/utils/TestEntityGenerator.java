package loans.utils;

import loans.domain.ServiceRequest;
import loans.repository.LoanEntity;
import loans.repository.RepositoryStatus;

import java.util.ArrayList;
import java.util.Date;

public class TestEntityGenerator {

    public Iterable<LoanEntity> generateLoanEntityList(int itemCount) {
        ArrayList<LoanEntity> loanEntities = new ArrayList<LoanEntity>(itemCount);
        for (int i = 0; i < itemCount; i++) {
            loanEntities.add(generateLoanEntity(true, RepositoryStatus.ACCEPTED));
        }
        return loanEntities;
    }

    public LoanEntity generateLoanEntity(boolean extended, RepositoryStatus repositoryStatus) {
        return new LoanEntity.LoanEntityBuilder()
                .withIpAddress("192.168.1.1")
                .withAmount(30.0)
                .withTerm(30)
                .withExtended(extended)
                .withStatus(repositoryStatus)
                .withApplicationDate(new Date())
                .build();
    }

    public ServiceRequest generateValidServiceRequest() {
        return new ServiceRequest.ServiceRequestBuilder()
                .withIpAddress("192.168.1.2")
                .withAmount(30.0)
                .withTerm(30)
                .build();
    }
    public ServiceRequest generateInvalidServiceRequest(Double amount, Integer term ) {
        return new ServiceRequest.ServiceRequestBuilder()
                .withIpAddress("192.168.1.2")
                .withAmount(amount)
                .withTerm(term)
                .build();
    }
}
