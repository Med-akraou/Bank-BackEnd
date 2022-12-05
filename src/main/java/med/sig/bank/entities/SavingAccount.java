package med.sig.bank.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
@Entity
@DiscriminatorValue("S") 
public class SavingAccount extends BankAccount {
    private double interestRate;
}
