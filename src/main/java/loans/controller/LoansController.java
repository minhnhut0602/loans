package loans.controller;

import loans.domain.ServiceResponse;
import loans.service.LoanApplicationService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import static loans.domain.ServiceRequest.ServiceRequestBuilder;

@RestController
public class LoansController {

    private final LoanApplicationService loanApplicationService;

    @Inject
    public LoansController(final LoanApplicationService loanApplicationService) {
        this.loanApplicationService = loanApplicationService;
    }

    @RequestMapping("/apply")
    public ServiceResponse apply(HttpServletRequest request,
                                 @RequestParam(value = "amount", required = true) Double amount,
                                 @RequestParam(value = "term", required = true) Integer term) {
        return loanApplicationService.apply(new ServiceRequestBuilder()
                .withAmount(amount)
                .withTerm(term)
                .withIpAddress(request.getRemoteAddr())
                .build());
    }

    @RequestMapping("/extend")
    public ServiceResponse extend(HttpServletRequest request) {
        return loanApplicationService.extend(new ServiceRequestBuilder()
                .withIpAddress(request.getRemoteAddr())
                .build());
    }

    @RequestMapping("/history")
    public ServiceResponse getHistory() {
        return loanApplicationService.getHistory();
    }
}
