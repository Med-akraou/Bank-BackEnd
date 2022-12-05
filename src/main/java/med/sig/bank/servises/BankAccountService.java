package med.sig.bank.servises;

import java.util.List;

import med.sig.bank.dtos.CustomerDTO;
import med.sig.bank.entities.BankAccount;
import med.sig.bank.entities.CurrentAccount;
import med.sig.bank.entities.Customer;
import med.sig.bank.entities.SavingAccount;

public interface BankAccountService {

	CustomerDTO saveCustomer(CustomerDTO costomerDTO);
	CustomerDTO getCustomer(Long id);
	List<CustomerDTO> litCustomers();
	CustomerDTO updateCostumer(CustomerDTO cuDto);
	void deleteCustomer(Long id);
	CurrentAccount saveCurrentAccount(double initialBalance, double overDraft, Long customerId);
	SavingAccount saveSavingAccount(double initialBalance, double interestRate, Long customerId);
	void debit(String accountId, double amount, String description);
	void credit(String accountId, double amount, String description);
	void transfer(String accountIdSource, String accountIdDestination, double amount);
	
	
	
	
}
