package med.sig.bank.mappers;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import med.sig.bank.dtos.CurrentAccountDTO;
import med.sig.bank.dtos.CustomerDTO;
import med.sig.bank.dtos.OperationDTO;
import med.sig.bank.dtos.SavingAccountDTO;
import med.sig.bank.entities.CurrentAccount;
import med.sig.bank.entities.Customer;
import med.sig.bank.entities.Operation;
import med.sig.bank.entities.SavingAccount;

@Service
public class BankMapper {
	public CustomerDTO fromCustmor(Customer customer) {
		CustomerDTO customerDTO = new CustomerDTO();
		BeanUtils.copyProperties(customer, customerDTO);
		return customerDTO;
	}

	public Customer fromCustomerDTO(CustomerDTO customerDTO) {
		Customer customer = new Customer();
		BeanUtils.copyProperties(customerDTO, customer);
		return customer;
	}

	public SavingAccountDTO fromSavingAccount( SavingAccount savingAccount) {
		SavingAccountDTO savingAccountDTO = new SavingAccountDTO();
		BeanUtils.copyProperties(savingAccount, savingAccountDTO);
		savingAccountDTO.setCustomerDTO(fromCustmor(savingAccount.getCustomer()));
		savingAccountDTO.setType(savingAccount.getClass().getSimpleName());
		return savingAccountDTO;
	}
	
	public SavingAccount fromSavingAccountDTO(SavingAccountDTO savingAccountDTO) {
		SavingAccount savingAccount = new SavingAccount();
		BeanUtils.copyProperties(savingAccountDTO, savingAccount);
		savingAccount.setCustomer(fromCustomerDTO(savingAccountDTO.getCustomerDTO()));
		return savingAccount;
	}
	
	public CurrentAccountDTO fromCurrentAccount(CurrentAccount currentAccount) {
		CurrentAccountDTO currentAccountDTO = new CurrentAccountDTO();
		BeanUtils.copyProperties(currentAccount, currentAccountDTO);
		currentAccountDTO.setCustomerDTO(fromCustmor(currentAccount.getCustomer()));
		currentAccountDTO.setType(currentAccount.getClass().getSimpleName());
		return currentAccountDTO;
	}
	
	public CurrentAccount fromCurrentAccountDTO(CurrentAccountDTO currentAccountDTO) {
		CurrentAccount account = new CurrentAccount();
		BeanUtils.copyProperties(currentAccountDTO, account);
		account.setCustomer(fromCustomerDTO(currentAccountDTO.getCustomerDTO()));
		return account;
	}
	
	public OperationDTO fromOperation(Operation operation) {
		OperationDTO operationDTO = new OperationDTO();
		BeanUtils.copyProperties(operation, operationDTO);
		return operationDTO;
	}
}
