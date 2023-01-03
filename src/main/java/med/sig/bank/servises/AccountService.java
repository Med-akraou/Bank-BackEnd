package med.sig.bank.servises;

import java.util.List;

import med.sig.bank.dtos.AccountHistoryDTO;
import med.sig.bank.dtos.BankAccountDTO;
import med.sig.bank.dtos.CreateCurrentAccountDTO;
import med.sig.bank.dtos.CreateSavingAccountDTO;
import med.sig.bank.dtos.CurrentAccountDTO;
import med.sig.bank.dtos.SavingAccountDTO;

public interface AccountService {

	/**
	 * Add new current account
	 * @param acc Current account to add
	 * @return the added current account
	 */
	CurrentAccountDTO saveCurrentAccount(CreateCurrentAccountDTO acc);

	/**
	 * Add new saving account
	 * @param acc Saving Account to save
	 * @return the added saving account
	 */
	SavingAccountDTO saveSavingAccount(CreateSavingAccountDTO acc);

	/**
	 * Get account by id
	 * @param id account id
	 * @return the account
	 */
	BankAccountDTO getAccount(String id);

	/**
	 *
	 * @return all account
	 */
	List<BankAccountDTO> listAccounts();

	/**
	 * Debit an account
	 * @param accountId account id to debit
	 * @param amount Amount
	 * @param description Description of operation
	 */
	void debit(String accountId, double amount, String description);

	/**
	 * Credit an account
	 * @param accountId account id to credit
	 * @param amount Amount
	 * @param description Description of operation
	 */
	void credit(String accountId, double amount, String description);

	/**
	 * Transfer an amount from an account to another
	 * @param accountIdSource Source account id
	 * @param accountIdDestination Destination account id
	 * @param amount Amount
	 */
	void transfer(String accountIdSource, String accountIdDestination, double amount);

	/**
	 * Get operations of an account
	 * @param accountId Account id
	 * @param page page Num
	 * @param size size of page
	 * @return the History of an account
	 */
	AccountHistoryDTO getAccountHistory(String accountId, int page, int size);

}
