package resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class MainRsourceController {
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/apply")
    public MainResource apply(@RequestParam (value = "amount",required = true,defaultValue = "11111") String amount,
                              @RequestParam (value = "term",required = true,defaultValue = "2222222")String term) {
        return new MainResource(counter.incrementAndGet(),
                String.format(template, amount+term));
    }
    @RequestMapping("/extend")
    public MainResource extend(@RequestParam(value="term", required=false, defaultValue="World") String term) {
        return new MainResource(counter.incrementAndGet(),
                String.format(template, term));
    }
    @RequestMapping("/history")
    public MainResource getHistory() {
        return new MainResource(counter.incrementAndGet(),
                String.format(template, "XXXXX"));
    }
}
