package med.sig.bank.dtos;

import java.util.Date;

import lombok.Data;
import med.sig.bank.enums.AccountStatus;

@Data
public class SavingAccountDTO extends BankAccountDTO{

	private String id;
	private double balance;
    private Date createAt;
    private AccountStatus status;
    private CustomerDTO customerDTO;
    private double interestRate;
}
