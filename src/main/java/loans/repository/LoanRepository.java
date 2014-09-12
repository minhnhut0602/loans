package loans.repository;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LoanRepository extends CrudRepository<LoanEntity, String> {

    List<LoanEntity> findByIpAddress(String ipAddress);

    LoanEntity findByStatus(ApplicationStatus status);
}
