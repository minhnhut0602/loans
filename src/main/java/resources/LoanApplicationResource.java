package resources;

import application.LoanApplicationServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoanApplicationResource {

    LoanApplicationService loanApplicationService = new LoanApplicationServiceImpl();

    @RequestMapping("/apply")
    public ServiceResponse apply(@RequestParam(value = "amount", required = true, defaultValue = "11111") String amount,
                                 @RequestParam(value = "term", required = true, defaultValue = "2222222") String term) {
        return loanApplicationService.apply(new ServiceRequest.ServiceRequestBuilder()
                .withAmount(amount)
                .withTerm(term)
                .build());
    }

    @RequestMapping("/extend")
    public ServiceResponse extend(@RequestParam(value = "term", required = false, defaultValue = "xxxxxxx") String term) {
        return loanApplicationService.extend(new ServiceRequest.ServiceRequestBuilder()
                .withTerm(term)
                .build());
    }

    @RequestMapping("/history")
    public ServiceResponse getHistory() {
        return loanApplicationService.getHistory();
    }
}
