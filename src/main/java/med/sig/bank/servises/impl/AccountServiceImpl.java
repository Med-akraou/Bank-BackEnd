package med.sig.bank.servises.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import med.sig.bank.exceptions.BalanceNotSufficientException;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import med.sig.bank.dtos.AccountHistoryDTO;
import med.sig.bank.dtos.AccountOperationDTO;
import med.sig.bank.dtos.BankAccountDTO;
import med.sig.bank.dtos.CreateCurrentAccountDTO;
import med.sig.bank.dtos.CreateSavingAccountDTO;
import med.sig.bank.dtos.CurrentAccountDTO;
import med.sig.bank.dtos.SavingAccountDTO;
import med.sig.bank.entities.BankAccount;
import med.sig.bank.entities.CurrentAccount;
import med.sig.bank.entities.Customer;
import med.sig.bank.entities.Operation;
import med.sig.bank.entities.SavingAccount;
import med.sig.bank.enums.AccountStatus;
import med.sig.bank.enums.OperationType;
import med.sig.bank.exceptions.NotFoundAccountException;
import med.sig.bank.exceptions.NotFoundCustomerException;
import med.sig.bank.mappers.BankMapper;
import med.sig.bank.repositeries.BankAccountRepository;
import med.sig.bank.repositeries.CustomerRepository;
import med.sig.bank.repositeries.OperationRepository;
import med.sig.bank.servises.AccountService;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class AccountServiceImpl implements AccountService {
	
	private final CustomerRepository customerRepository;
	private final BankAccountRepository bankAccountRepository;
	private final OperationRepository operationRepository;
	private final BankMapper mapper;

	@Override
	public CurrentAccountDTO saveCurrentAccount(CreateCurrentAccountDTO acc) {
		Customer customer=customerRepository.findById(acc.getCustomerId()).orElse(null);
        if(customer==null)
            throw new NotFoundCustomerException("Customer not found");
		log.info("Creating new Current Account for customer {} {}", customer.getFirstname(),customer.getLastname());
        CurrentAccount currentAccount=new CurrentAccount();
        BeanUtils.copyProperties(acc, currentAccount);
        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setCreateAt(new Date());
        currentAccount.setStatus(AccountStatus.CREATED);
        currentAccount.setCustomer(customer);
        CurrentAccount savedBankAccount = bankAccountRepository.save(currentAccount);
		log.info("Account Created");
        return mapper.toCurrentAccountDTO(savedBankAccount);
	}

	@Override
	public SavingAccountDTO saveSavingAccount(CreateSavingAccountDTO acc) {
		Customer customer=customerRepository.findById(acc.getCustomerId()).orElse(null);
        if(customer==null)
            throw new NotFoundCustomerException("Customer not found");
		log.info("Creating new Saving Account for user {} {}",customer.getFirstname(),customer.getLastname());
        SavingAccount savingAccount=new SavingAccount();
        BeanUtils.copyProperties(acc, savingAccount);
        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setCreateAt(new Date());
        savingAccount.setStatus(AccountStatus.CREATED);
        savingAccount.setCustomer(customer);
        SavingAccount savedBankAccount = bankAccountRepository.save(savingAccount);
		log.info("Account created");
        return mapper.toSavingAccountDTO(savedBankAccount);
	}
	
	@Override
	public BankAccountDTO getAccount(String id) {
		BankAccount account = bankAccountRepository.findById(id)
				.orElseThrow(()-> new NotFoundAccountException("Account not found"));
		if(account instanceof SavingAccount) {
			log.info("get saving account");
			SavingAccount savingAccount = (SavingAccount)account;
			return mapper.toSavingAccountDTO(savingAccount);
		}
		
		else {
			log.info("get current account");
			CurrentAccount currentAccount = (CurrentAccount)account;
			return mapper.toCurrentAccountDTO(currentAccount);
		}
	}

	@Override
	public List<BankAccountDTO> listAccounts() {
		log.info("get all accounts");
		List<BankAccount> accounts = bankAccountRepository.findAll();
		List<BankAccountDTO> accountsDTO = accounts.stream().map(acc ->{
		  if(acc instanceof CurrentAccount) {
			  CurrentAccount currentAccount = (CurrentAccount)acc;
			  return mapper.toCurrentAccountDTO(currentAccount);
		  }else {
			  SavingAccount savingAccount = (SavingAccount) acc;
			  return mapper.toSavingAccountDTO(savingAccount);
		  }
		}).collect(Collectors.toList());
		
		return accountsDTO;
	}

	@Override
	public void debit(String accountId, double amount, String description) {
		BankAccount account = bankAccountRepository.findById(accountId)
				.orElseThrow(()-> new NotFoundAccountException("Account not found"));
		if(account.getBalance()<amount){
			log.warn("Balance not sufficient");
			throw new BalanceNotSufficientException("Balance not sufficient");
		}
        log.info("debit account");
		Operation operation = new Operation();
		operation.setType(OperationType.DEBIT);
		operation.setAmount(amount);
		operation.setDescription(description);
		operation.setOperationDate(new Date());
		operation.setAccount(account);
		operationRepository.save(operation);
		account.setBalance(account.getBalance()-amount);
		bankAccountRepository.save(account);
		
	}

	@Override
	public void credit(String accountId, double amount, String description) {
		BankAccount account = bankAccountRepository.findById(accountId)
				.orElseThrow(()-> new NotFoundAccountException("Account not found"));
		log.info("credit account");
		Operation operation = new Operation();
		operation.setType(OperationType.CREDIT);
		operation.setAmount(amount);
		operation.setDescription(description);
		operation.setOperationDate(new Date());
		operation.setAccount(account);
		operationRepository.save(operation);
		account.setBalance(account.getBalance()+amount);
		bankAccountRepository.save(account);

	}

	@Override
	public void transfer(String accountIdSource, String accountIdDestination, double amount) {
		log.info("transfer operation from account with id {} to account with id {}",accountIdSource,accountIdDestination);
		this.debit(accountIdSource, amount, "Tronsfer to "+accountIdDestination);
		this.credit(accountIdDestination, amount, "Transfer from "+ accountIdSource);
		log.info("validated transfer");
	}

	@Override
	public AccountHistoryDTO getAccountHistory(String accountId, int page, int size) {
		BankAccount account = bankAccountRepository.findById(accountId)
				.orElseThrow(()-> new 
						NotFoundAccountException("Account not found"));
		log.info("get account(id={}) operations page = {}",account.getId(),page);
		Page<Operation> operations = operationRepository.findByAccountIdOrderByOperationDateDesc(accountId,PageRequest.of(page, size));
		AccountHistoryDTO accountHistoryDTO = new AccountHistoryDTO();
		List<AccountOperationDTO> operationDto = operations.stream().map(op -> mapper.toOperationDTO(op)).collect(Collectors.toList());
		accountHistoryDTO.setOperations(operationDto);
		accountHistoryDTO.setBalance(account.getBalance());
		accountHistoryDTO.setCurrentPage(page);
		accountHistoryDTO.setSizePage(size);
		accountHistoryDTO.setTotalPage(operations.getTotalPages());
		return accountHistoryDTO;
	}


}
