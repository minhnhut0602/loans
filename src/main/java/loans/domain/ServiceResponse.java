package loans.domain;

import loans.repository.LoanEntity;

import java.util.List;

public class ServiceResponse {

    private final Iterable<LoanEntity> historyItems;
    private final String message;

    public ServiceResponse(ServiceResponseBuilder builder) {
        this.historyItems = builder.historyItems;
        this.message = builder.message;
    }

    public static class ServiceResponseBuilder {
        private Iterable<LoanEntity> historyItems;
        private String message;

        public ServiceResponseBuilder withHistoryItems(Iterable<LoanEntity> historyItems) {
            this.historyItems = historyItems;
            return this;
        }

        public ServiceResponseBuilder withMessage(String message) {
            this.message = message;
            return this;
        }

        public ServiceResponse build() {
            return new ServiceResponse(this);
        }
    }

    public Iterable<LoanEntity> getHistoryItems() {
        return historyItems;
    }

    public String getMessage() {
        return message;
    }
}
