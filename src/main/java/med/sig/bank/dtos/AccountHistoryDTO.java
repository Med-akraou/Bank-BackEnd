package med.sig.bank.dtos;

import java.util.List;

import lombok.Data;

@Data
public class AccountHistoryDTO {
	private String accountId;
	private double balance;
	private int currentPage;
	private int totalPage;
	private int sizePage;
	private List<AccountOperationDTO> operations;
}
