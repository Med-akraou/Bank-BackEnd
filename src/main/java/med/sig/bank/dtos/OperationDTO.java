package med.sig.bank.dtos;

import java.util.Date;

import med.sig.bank.enums.OperationType;

public class OperationDTO {
	private Long id;
    private Date OperationDate;
    private double amount;
    private OperationType type;
    private String descreption;
}
