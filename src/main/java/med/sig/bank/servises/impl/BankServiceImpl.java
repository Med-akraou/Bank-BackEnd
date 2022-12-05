package med.sig.bank.servises.impl;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import med.sig.bank.entities.CurrentAccount;
import med.sig.bank.entities.Customer;
import med.sig.bank.entities.SavingAccount;
import med.sig.bank.repositeries.BankAccountRepositery;
import med.sig.bank.repositeries.CustomerRepositery;
import med.sig.bank.repositeries.OperationRepositery;
import med.sig.bank.servises.BankAccountService;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BankServiceImpl implements BankAccountService {
	
	private CustomerRepositery customerRepositery;
	private BankAccountRepositery bankAccountRepositery;
	private OperationRepositery operationRepositery;
	
	
	@Override
	public Customer saveCustomer(Customer costomer) {
		// TODO Auto-generated method stub
		log.info("saving new customer");
		return null;
	}

	@Override
	public CurrentAccount saveCurrentAccount(double initialBalance, double overDraft, Long customerId) {
		
		return null;
	}

	@Override
	public SavingAccount saveSavingAccount(double initialBalance, double interestRate, Long customerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void debit(String accountId, double amount, String description) {
		// TODO Auto-generated method stub

	}

	@Override
	public void credit(String accountId, double amount, String description) {
		// TODO Auto-generated method stub

	}

	@Override
	public void transfer(String accountIdSource, String accountIdDestination, double amount) {
		// TODO Auto-generated method stub

	}

}
