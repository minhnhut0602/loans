package loans.repository;

public enum ApplicationStatus {
    ACCEPTED("ACCEPTED"),
    DECLINED("DECLINED"),
    EXTENSION("EXTENSION");

    private String value;

    ApplicationStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
