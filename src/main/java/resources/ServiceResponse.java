package resources;

public class ServiceResponse {

    private final String status;
    private final String message;

    public ServiceResponse(ServiceResponseBuilder builder) {
        this.status = builder.status;
        this.message = builder.message;
    }

    public static class ServiceResponseBuilder {
        private String status;
        private String message;

        public ServiceResponseBuilder withStatus(String status) {
            this.status = status;
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
