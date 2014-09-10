package repository;/*
 * @(#)LoanEntity.java
 *
 * Copyright C.T.Co Ltd, 15/25 Jurkalnes Street, Riga LV-1046, Latvia. All rights reserved.
 */

import org.springframework.data.annotation.Id;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class LoanEntity {

    @Id
    @Column(name = "amount", nullable = false, updatable = false)
    @NotNull
    @Size(max = 64)
    private String amount;

    @Column(name = "term", nullable = false, updatable = true)
    @NotNull
    @Size(max = 64)
    private String term;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }
}
