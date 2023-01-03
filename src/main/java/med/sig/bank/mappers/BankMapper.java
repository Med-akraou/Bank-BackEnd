package med.sig.bank.mappers;

import lombok.RequiredArgsConstructor;
import med.sig.bank.dtos.AccountOperationDTO;
import med.sig.bank.dtos.CurrentAccountDTO;
import med.sig.bank.dtos.SavingAccountDTO;
import med.sig.bank.entities.CurrentAccount;
import med.sig.bank.entities.Operation;
import med.sig.bank.entities.SavingAccount;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BankMapper {
	private final CustomerMapper customerMapper;

	public SavingAccountDTO toSavingAccountDTO(SavingAccount savingAccount) {
		SavingAccountDTO savingAccountDTO = new SavingAccountDTO();
		BeanUtils.copyProperties(savingAccount, savingAccountDTO);
		savingAccountDTO.setCustomerDTO(customerMapper.toCustomerDto(savingAccount.getCustomer()));
		savingAccountDTO.setType(savingAccount.getClass().getSimpleName());
		return savingAccountDTO;
	}
	
	public SavingAccount fromSavingAccountDTO(SavingAccountDTO savingAccountDTO) {
		SavingAccount savingAccount = new SavingAccount();
		BeanUtils.copyProperties(savingAccountDTO, savingAccount);
		savingAccount.setCustomer(customerMapper.toCustomer(savingAccountDTO.getCustomerDTO()));
		return savingAccount;
	}
	
	public CurrentAccountDTO toCurrentAccountDTO(CurrentAccount currentAccount) {
		CurrentAccountDTO currentAccountDTO = new CurrentAccountDTO();
		BeanUtils.copyProperties(currentAccount, currentAccountDTO);
		currentAccountDTO.setCustomerDTO(customerMapper.toCustomerDto(currentAccount.getCustomer()));
		currentAccountDTO.setType(currentAccount.getClass().getSimpleName());
		return currentAccountDTO;
	}
	
	public CurrentAccount fromCurrentAccountDTO(CurrentAccountDTO currentAccountDTO) {
		CurrentAccount account = new CurrentAccount();
		BeanUtils.copyProperties(currentAccountDTO, account);
		account.setCustomer(customerMapper.toCustomer(currentAccountDTO.getCustomerDTO()));
		return account;
	}
	
	public AccountOperationDTO toOperationDTO(Operation operation) {
		AccountOperationDTO operationDTO = new AccountOperationDTO();
		BeanUtils.copyProperties(operation, operationDTO);
		return operationDTO;
	}
}
