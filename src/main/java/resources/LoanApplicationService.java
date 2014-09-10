package resources;

public interface LoanApplicationService {


    ServiceResponse apply(ServiceRequest request);

    ServiceResponse extend(ServiceRequest request);

    ServiceResponse getHistory();
}
