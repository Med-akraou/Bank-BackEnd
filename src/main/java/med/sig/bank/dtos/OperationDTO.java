package med.sig.bank.dtos;

import lombok.Data;

@Data
public class OperationDTO {

	private String accountId;
	private double amount;
	private String description;
}
