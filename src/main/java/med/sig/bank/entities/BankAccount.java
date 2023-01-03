package med.sig.bank.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import med.sig.bank.enums.AccountStatus;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE",length = 4)
public class BankAccount {
    @Id
    private String id;
    private double balance;
    private Date createAt;
    private AccountStatus status; 
     
    @ManyToOne(fetch = FetchType.EAGER)
    private Customer customer;


    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Operation> operations ;
}
