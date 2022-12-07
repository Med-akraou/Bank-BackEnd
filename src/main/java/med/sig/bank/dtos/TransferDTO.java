package med.sig.bank.dtos;

import lombok.Data;

@Data
public class TransferDTO {

	private String accountSource;
	private String accountDestination;
	private double amount;
	private String descreption;
}
