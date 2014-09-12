package loans.repository;/*
 * @(#)ApplicationStatus.java
 *
 * Copyright C.T.Co Ltd, 15/25 Jurkalnes Street, Riga LV-1046, Latvia. All rights reserved.
 */

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
