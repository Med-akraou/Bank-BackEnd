package med.sig.bank.dtos;

import lombok.Data;

@Data
public class CreateCurrentAccountDTO {

	private double balance; 
	private double overDraft; 
	private Long customerId;
	
}
