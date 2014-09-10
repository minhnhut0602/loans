package loans.repository;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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

    public LoanEntity() {
    }

    public LoanEntity(LoanEntityBuilder builder) {
        this.amount = builder.amount;
        this.term = builder.term;
    }

    public static class LoanEntityBuilder {
        private String amount;
        private String term;

        public LoanEntityBuilder withAmount(String amount) {
            this.amount = amount;
            return this;
        }

        public LoanEntityBuilder withTerm(String term) {
            this.term = term;
            return this;
        }

        public LoanEntity build() {
            return new LoanEntity(this);
        }
    }
}
