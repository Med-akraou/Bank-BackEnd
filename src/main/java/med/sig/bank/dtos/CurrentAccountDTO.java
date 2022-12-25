package med.sig.bank.dtos;

import java.util.Date;

import lombok.Data;
import med.sig.bank.enums.AccountStatus;

@Data
public class CurrentAccountDTO extends BankAccountDTO {

    private double overDraft;
}
