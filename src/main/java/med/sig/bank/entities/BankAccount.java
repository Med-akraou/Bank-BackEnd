package med.sig.bank.entities;

import java.util.ArrayList ;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import med.sig.bank.enums.AccountStatus;

@Data @AllArgsConstructor @NoArgsConstructor
@Entity @Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE",length = 4)
public class BankAccount {
    @Id
    private String id;
    private double balance;
    private Date createAt;
    private AccountStatus status; 
     
    @ManyToOne
    private Customer customer;


    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Operation> operations ;
}
