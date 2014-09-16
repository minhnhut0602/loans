package loans.repository;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class LoanEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "amount", nullable = false, updatable = false)
    @NotNull
    private String amount;

    @Column(name = "term", nullable = false, updatable = true)
    @NotNull
    private String term;

    @Column(name = "ipAddress", nullable = false, updatable = false)
    @NotNull
    private String ipAddress;

    @Column(name = "applicationDate", nullable = false, updatable = false)
    @NotNull
    private Date applicationDate;

    @Column(name = "status", nullable = false, updatable = true)
    @NotNull
    private RepositoryStatus status;

    @Column(name = "extended")
    private boolean extended;

    public LoanEntity() {
    }

    public LoanEntity(LoanEntityBuilder builder) {
        this.amount = builder.amount.toString();
        this.term = builder.term.toString();
        this.ipAddress = builder.ipAddress;
        this.status = builder.status;
        this.extended = builder.extended;

    }

    public static class LoanEntityBuilder {
        private Double amount;
        private Integer term;
        private String ipAddress;
        private RepositoryStatus status;
        private boolean extended;

        public LoanEntityBuilder withAmount(Double amount) {
            this.amount = amount;
            return this;
        }

        public LoanEntityBuilder withTerm(Integer term) {
            this.term = term;
            return this;
        }

        public LoanEntityBuilder withIpAddress(String ipAddress) {
            this.ipAddress = ipAddress;
            return this;
        }

        public LoanEntityBuilder withStatus(RepositoryStatus status) {
            this.status = status;
            return this;
        }

        public LoanEntityBuilder withExtended(boolean extended) {
            this.extended = extended;
            return this;
        }


        public LoanEntity build() {
            return new LoanEntity(this);
        }
    }

    @PrePersist
    void createdAt() {
        this.applicationDate = new Date();
    }

    public String getAmount() {
        return amount;
    }

    public String getTerm() {
        return term;
    }

    public Date getApplicationDate() {
        return applicationDate;
    }

    public RepositoryStatus getStatus() {
        return status;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void setStatus(RepositoryStatus status) {
        this.status = status;
    }

    public void setExtended(boolean extended) {
        this.extended = extended;
    }

    public boolean isExtended() {
        return extended;
    }
}
