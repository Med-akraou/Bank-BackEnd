package med.sig.bank.servises.impl;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import med.sig.bank.dtos.CreateCurrentAccountDTO;
import med.sig.bank.dtos.CreateSavingAccountDTO;
import med.sig.bank.dtos.CurrentAccountDTO;
import med.sig.bank.dtos.SavingAccountDTO;
import med.sig.bank.entities.CurrentAccount;
import med.sig.bank.entities.Customer;
import med.sig.bank.entities.SavingAccount;
import med.sig.bank.enums.AccountStatus;
import med.sig.bank.exceptions.NotFoundCustomerException;
import med.sig.bank.mappers.BankMapper;
import med.sig.bank.repositeries.BankAccountRepositery;
import med.sig.bank.repositeries.CustomerRepositery;
import med.sig.bank.repositeries.OperationRepositery;
import med.sig.bank.servises.AccountService;

@AllArgsConstructor
@Service
@Slf4j
@Transactional
public class AccountServiceImpl implements AccountService {
	
	private CustomerRepositery customerRepository;
	private BankAccountRepositery bankAccountRepository;
	private OperationRepositery operationRepository;
	private BankMapper mapper;

	@Override
	public CurrentAccountDTO saveCurrentAccount(CreateCurrentAccountDTO acc) {
		Customer customer=customerRepository.findById(acc.getCustomerId()).orElse(null);
        if(customer==null)
            throw new NotFoundCustomerException("Customer not found");
        CurrentAccount currentAccount=new CurrentAccount();
        BeanUtils.copyProperties(acc, currentAccount);
        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setCreateAt(new Date());
        currentAccount.setStatus(AccountStatus.CREATED);
        currentAccount.setCustomer(customer);
        CurrentAccount savedBankAccount = bankAccountRepository.save(currentAccount);
        return mapper.fromCurrentAccount(savedBankAccount);
	}

	@Override
	public SavingAccountDTO saveSavingAccount(CreateSavingAccountDTO acc) {
		Customer customer=customerRepository.findById(acc.getCustomerId()).orElse(null);
        if(customer==null)
            throw new NotFoundCustomerException("Customer not found");
        SavingAccount savingAccount=new SavingAccount();
        BeanUtils.copyProperties(acc, savingAccount);
        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setCreateAt(new Date());
        savingAccount.setStatus(AccountStatus.CREATED);
        savingAccount.setCustomer(customer);
        SavingAccount savedBankAccount = bankAccountRepository.save(savingAccount);
        return mapper.fromSavingAccount(savedBankAccount);
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
