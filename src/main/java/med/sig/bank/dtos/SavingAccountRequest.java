package med.sig.bank.dtos;

import lombok.Data;

@Data
public class SavingAccountRequest {
	
	private double balance; 
	private double interestRate; 
	private String customerId;

}
