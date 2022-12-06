package med.sig.bank.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import med.sig.bank.dtos.CreateCurrentAccountDTO;
import med.sig.bank.dtos.CreateSavingAccountDTO;
import med.sig.bank.dtos.CurrentAccountDTO;
import med.sig.bank.dtos.SavingAccountDTO;
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
}
