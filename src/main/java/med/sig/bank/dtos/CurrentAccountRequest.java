package med.sig.bank.dtos;

import lombok.Data;

@Data
public class CurrentAccountRequest {

	private double balance; 
	private double overDraft; 
	private String customerId;
	
}
