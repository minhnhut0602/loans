package loans.service;

public enum StatusMessage {
    OK("Okay"),
    INVALID_AMOUNT("Invalid Amount"),
    INVALID_TERM("Invalid Term"),
    POSSIBLE_FRAUD("Possible Fraud"),
    POSSIBLE_SPAM("Possible Spam"),
    ALREADY_IN_PROGRESS("Loan Already Accepted"),
    SAVING_ERROR("Error while saving entity"),
    ALREADY_EXTENDED("The loan is already extended"),
    NOTHING_TO_EXTEND("No actual loans to extend"),
    HISTORY_EMPTY("No entries in history to show");

    private String value;

    StatusMessage(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
