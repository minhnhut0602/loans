package loans.service;

public enum ValidationStatus {
    OK("Okay"),
    INVALID_AMOUNT("Invalid Amount"),
    INVALID_TERM("Invalid Term"),
    POSSIBLE_FRAUD("Possible Fraud"),
    POSSIBLE_SPAM("Possible Spam"),
    ALREADY_IN_PROGRESS("Loan Already Accepted");

    private String value;

    ValidationStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
