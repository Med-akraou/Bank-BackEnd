package med.sig.bank.servises;

import java.util.List;

import med.sig.bank.dtos.AccountHistoryDTO;
import med.sig.bank.dtos.BankAccountDTO;
import med.sig.bank.dtos.CreateCurrentAccountDTO;
import med.sig.bank.dtos.CreateSavingAccountDTO;
import med.sig.bank.dtos.CurrentAccountDTO;
import med.sig.bank.dtos.SavingAccountDTO;

public interface AccountService {

	CurrentAccountDTO saveCurrentAccount(CreateCurrentAccountDTO acc);

	SavingAccountDTO saveSavingAccount(CreateSavingAccountDTO acc);
	
	BankAccountDTO getAccount(String id);
	
	List<BankAccountDTO> listAccounts();

	void debit(String accountId, double amount, String description);

	void credit(String accountId, double amount, String description);

	void transfer(String accountIdSource, String accountIdDestination, double amount);
	
	public AccountHistoryDTO getAcoountHistory(String accountId, int page, int size);
	

}
