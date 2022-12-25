package med.sig.bank.dtos;

import java.util.Date;

import lombok.Data;
import med.sig.bank.enums.AccountStatus;

@Data
public class SavingAccountDTO extends BankAccountDTO{

    private double interestRate;
}
