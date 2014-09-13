package loans.domain;

import loans.repository.LoanEntity;

public class ServiceResponse {

    private final Iterable<LoanEntity> historyItems;
    private final String statusMessage;

    public ServiceResponse(ServiceResponseBuilder builder) {
        this.historyItems = builder.historyItems;
        this.statusMessage = builder.statusMessage;
    }

    public static class ServiceResponseBuilder {
        private Iterable<LoanEntity> historyItems;
        private String statusMessage;

        public ServiceResponseBuilder withHistoryItems(Iterable<LoanEntity> historyItems) {
            this.historyItems = historyItems;
            return this;
        }

        public ServiceResponseBuilder withMessage(String statusMessage) {
            this.statusMessage = statusMessage;
            return this;
        }

        public ServiceResponse build() {
            return new ServiceResponse(this);
        }
    }

    public Iterable<LoanEntity> getHistoryItems() {
        return historyItems;
    }

    public String getStatusMessage() {
        return statusMessage;
    }
}
