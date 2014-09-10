package resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoanApplicationResource {

    LoanApplicationService loanApplicationService;

    @RequestMapping("/apply")
    public ServiceResponse apply(@RequestParam(value = "amount", required = true, defaultValue = "11111") String amount,
                                         @RequestParam(value = "term", required = true, defaultValue = "2222222") String term) {
        return loanApplicationService.apply(new ServiceRequest(amount,term));
    }

    @RequestMapping("/extend")
    public ServiceResponse extend(@RequestParam(value = "term", required = false, defaultValue = "xxxxxxx") String term) {
        return loanApplicationService.extend(new ServiceRequest(term));
    }

    @RequestMapping("/history")
    public ServiceResponse getHistory() {
        return loanApplicationService.getHistory();
    }
}
