package loans.controller;

import loans.domain.ServiceRequest;
import loans.service.LoanApplicationService;
import loans.domain.ServiceResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

import java.util.ArrayList;

import static loans.domain.ServiceRequest.ServiceRequestBuilder;

@RestController
public class LoansController {

    private final LoanApplicationService loanApplicationService;

    @Inject
    public LoansController(final LoanApplicationService loanApplicationService) {
        this.loanApplicationService=loanApplicationService;
    }

    @RequestMapping("/apply")
    public ServiceResponse apply(@RequestParam(value = "amount", required = true) String amount,
                                 @RequestParam(value = "term", required = true) String term) {
        ServiceResponse serviceResponse = loanApplicationService.apply(new ServiceRequestBuilder()
                .withAmount(amount)
                .withTerm(term)
                .build());
        return serviceResponse;
    }

    @RequestMapping("/extend")
    public ServiceResponse extend(@RequestParam(value = "term", required = true) String term) {
        return loanApplicationService.extend(new ServiceRequestBuilder()
                .withTerm(term)
                .build());
    }

    @RequestMapping("/history")
    public ServiceResponse getHistory() {
        return loanApplicationService.getHistory();
    }
}
