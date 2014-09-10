package loans.domain;

public class ServiceRequest {

    private String amount;
    private String term;

    public ServiceRequest(ServiceRequestBuilder builder) {
        this.amount = builder.amount;
        this.term = builder.term;
    }

    public static class ServiceRequestBuilder {
        private String amount;
        private String term;

        public ServiceRequestBuilder withAmount(String amount) {
            this.amount = amount;
            return this;
        }

        public ServiceRequestBuilder withTerm(String term) {
            this.term = term;
            return this;
        }

        public ServiceRequest build() {
            return new ServiceRequest(this);
        }
    }

    public String getAmount() {
        return amount;
    }

    public String getTerm() {
        return term;
    }
}
