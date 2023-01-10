package med.sig.bank.dtos;

import lombok.Data;

@Data
public class SavingAccountDTO extends BankAccountDTO{

    private double interestRate;
}
