package med.sig.bank.dtos;

import java.util.Date;

import lombok.Data;
import med.sig.bank.enums.OperationType;

@Data
public class AccountOperationDTO {
	private Date operationDate;
	private double amount;
	private OperationType type;
	private String description;
}
