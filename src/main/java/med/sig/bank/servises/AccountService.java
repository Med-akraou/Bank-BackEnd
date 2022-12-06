package med.sig.bank.servises;

import med.sig.bank.dtos.CreateCurrentAccountDTO;
import med.sig.bank.dtos.CreateSavingAccountDTO;
import med.sig.bank.dtos.CurrentAccountDTO;
import med.sig.bank.dtos.SavingAccountDTO;

public interface AccountService {

	CurrentAccountDTO saveCurrentAccount(CreateCurrentAccountDTO acc);

	SavingAccountDTO saveSavingAccount(CreateSavingAccountDTO acc);

	void debit(String accountId, double amount, String description);

	void credit(String accountId, double amount, String description);

	void transfer(String accountIdSource, String accountIdDestination, double amount);

}
