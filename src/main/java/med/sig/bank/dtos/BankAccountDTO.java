package med.sig.bank.dtos;

import lombok.Data;
import med.sig.bank.enums.AccountStatus;

import java.util.Date;

@Data
public class BankAccountDTO {
	private String id;
	private double balance;
	private Date createAt;
	private AccountStatus status;
	private CustomerDTO customerDTO;
	private String type;
}
