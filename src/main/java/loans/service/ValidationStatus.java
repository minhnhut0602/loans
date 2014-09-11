package loans.service;/*
 * @(#)ValidationStatus.java
 *
 * Copyright C.T.Co Ltd, 15/25 Jurkalnes Street, Riga LV-1046, Latvia. All rights reserved.
 */

public enum ValidationStatus {
    OK,
    INVALID_AMOUNT,
    INVALID_TERM,
    POSSIBLE_FRAUD,
    POSSIBLE_SPAM
}
