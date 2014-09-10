package application;

import resources.LoanApplicationService;
import resources.ServiceRequest;
import resources.ServiceResponse;

public class LoanApplicationServiceImpl implements LoanApplicationService {
    @Override
    public ServiceResponse apply(ServiceRequest request) {
        ServiceResponse response=new ServiceResponse();
        response.setStatus("OK");
        response.setMessage("Performed apply");
        return response;
    }

    @Override
    public ServiceResponse extend(ServiceRequest request) {
        ServiceResponse response=new ServiceResponse();
        response.setStatus("OK");
        response.setMessage("Performed extend");
        return response;
    }

    @Override
    public ServiceResponse getHistory() {
        ServiceResponse response=new ServiceResponse();
        response.setStatus("OK");
        response.setMessage("Performed getHistory");
        return response;
    }
}
