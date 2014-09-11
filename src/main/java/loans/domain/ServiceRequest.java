package loans.domain;

public class ServiceRequest {

    private final Double amount;
    private final Integer term;
    private final String ipAddress;

    public ServiceRequest(ServiceRequestBuilder builder) {
        this.ipAddress = builder.ipAddress;
        this.amount = builder.amount;
        this.term = builder.term;
    }

    public static class ServiceRequestBuilder {
        private Double amount;
        private Integer term;
        private String ipAddress;

        public ServiceRequestBuilder withAmount(Double amount) {
            this.amount = amount;
            return this;
        }

        public ServiceRequestBuilder withTerm(Integer term) {
            this.term = term;
            return this;
        }

        public ServiceRequestBuilder withIpAddress(String ipAddress) {
            this.ipAddress = ipAddress;
            return this;
        }

        public ServiceRequest build() {
            return new ServiceRequest(this);
        }
    }

    public Double getAmount() {
        return amount;
    }

    public Integer getTerm() {
        return term;
    }

    public String getIpAddress() {
        return ipAddress;
    }
}
