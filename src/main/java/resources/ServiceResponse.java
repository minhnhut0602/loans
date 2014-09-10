package resources;

import java.util.List;

public class ServiceResponse {

    private final List<String> historyItems;
    private final String message;

    public ServiceResponse(ServiceResponseBuilder builder) {
        this.historyItems = builder.historyItems;
        this.message = builder.message;
    }

    public static class ServiceResponseBuilder {
        private List<String> historyItems;
        private String message;

        public ServiceResponseBuilder withHistoryItems(List<String> historyItems) {
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
}
