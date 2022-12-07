package med.sig.bank.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data @NoArgsConstructor @AllArgsConstructor 
@Entity 
@DiscriminatorValue("C")
public class CurrentAccount extends BankAccount {
    private double overDraft;
    
}
