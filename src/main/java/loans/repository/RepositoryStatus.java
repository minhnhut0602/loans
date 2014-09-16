package loans.repository;

public enum RepositoryStatus {
    ACCEPTED("ACCEPTED"),
    DECLINED("DECLINED"),
    EXTENSION("EXTENSION");

    private String value;

    RepositoryStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
