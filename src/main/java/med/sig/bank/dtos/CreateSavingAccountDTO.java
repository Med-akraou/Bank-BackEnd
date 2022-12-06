package med.sig.bank.dtos;

import lombok.Data;

@Data
public class CreateSavingAccountDTO {
	
	private double balance; 
	private double interestRate; 
	private Long customerId;

}
