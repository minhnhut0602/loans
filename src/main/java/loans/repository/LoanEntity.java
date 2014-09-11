package loans.repository;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
public class LoanEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "amount", nullable = false, updatable = false)
    @NotNull
    @Size(max = 64)
    private String amount;

    @Column(name = "term", nullable = false, updatable = true)
    @NotNull
    @Size(max = 64)
    private String term;

    @Column(name = "ipAddress",nullable = false,updatable = false)
    @NotNull
    private String ipAddress;

    @Column(name = "applicationDate",nullable = false,updatable = false)
    @NotNull
    private Date applicationDate;

    public LoanEntity() {
    }

    public LoanEntity(LoanEntityBuilder builder) {
        this.amount = builder.amount.toString();
        this.term = builder.term.toString();
        this.ipAddress=builder.ipAddress;
    }

    public static class LoanEntityBuilder {
        private Integer amount;
        private Integer term;
        private String ipAddress;

        public LoanEntityBuilder withAmount(Integer amount) {
            this.amount = amount;
            return this;
        }

        public LoanEntityBuilder withTerm(Integer term) {
            this.term = term;
            return this;
        }
        public LoanEntityBuilder withIpAddress(String ipAddress){
            this.ipAddress=ipAddress;
            return this;
        }

        public LoanEntity build() {
            return new LoanEntity(this);
        }
    }
    @PrePersist
    void createdAt(){
        this.applicationDate=new Date();
    }
}
