package med.sig.bank.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import med.sig.bank.dtos.BankAccountDTO;
import med.sig.bank.dtos.CreateCurrentAccountDTO;
import med.sig.bank.dtos.CreateSavingAccountDTO;
import med.sig.bank.dtos.CreditDebitDTO;
import med.sig.bank.dtos.CurrentAccountDTO;
import med.sig.bank.dtos.SavingAccountDTO;
import med.sig.bank.dtos.TransferDTO;
import med.sig.bank.servises.AccountService;

@RestController
@AllArgsConstructor
@Slf4j
public class AccountCotroller {

	private AccountService accountService;

	@PostMapping("/saveCurrentAccount")
	public CurrentAccountDTO createCurrentAccount(@RequestBody CreateCurrentAccountDTO acc) {
		return accountService.saveCurrentAccount(acc);
	}

	@PostMapping("/saveSavingAccount")
	public SavingAccountDTO createSavingAccount(@RequestBody CreateSavingAccountDTO acc) {
		return accountService.saveSavingAccount(acc);
	}

	@GetMapping("/accounts/{id}")
	public BankAccountDTO getAccount(@PathVariable(name = "id") String id) {
		return accountService.getAccount(id);
	}

	@GetMapping("/accounts")
	public List<BankAccountDTO> listAccounts() {
		return accountService.listAccounts();
	}
	
	@PostMapping("/accounts/debit")
	public void debit(@RequestBody CreditDebitDTO operation) {
		accountService.debit(operation.getAccountId(), operation.getAmount(), operation.getDescription());
	}
	
	@PostMapping("/accounts/credit")
	public void credit(@RequestBody CreditDebitDTO operation) {
		accountService.credit(operation.getAccountId(), operation.getAmount(), operation.getDescription());
	}
	
	@PostMapping("/accounts/transfer")
	public void transfer(@RequestBody TransferDTO transfer) {
		accountService.transfer(transfer.getAccountSource(), transfer.getAccountDestination(), transfer.getAmount());
	}
}
