package med.sig.bank.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import med.sig.bank.dtos.CustomerDTO;
import med.sig.bank.servises.BankAccountService;

@RestController
@AllArgsConstructor
@Slf4j
public class CustomerRestController {

	private BankAccountService bankAccountService;
	
	@PostMapping("/customers")
	public CustomerDTO adCustomer(@RequestBody CustomerDTO customerDTO) {
		return bankAccountService.saveCustomer(customerDTO);
	}
	
	@GetMapping("/customers/{id}")
	public CustomerDTO getCustomer(@PathVariable(name = "id") Long id) {
		return bankAccountService.getCustomer(id);
	}
	
	@GetMapping("/customers")
	public List<CustomerDTO> listCustmors(){
		return bankAccountService.litCustomers();
	}
	
	@PatchMapping("/customers/{id}")
	public CustomerDTO updateCustomer(@PathVariable(name = "id") Long id,@RequestBody CustomerDTO customerDTO) {
		customerDTO.setId(id);
		return bankAccountService.updateCostumer(customerDTO);
	}
	
	@DeleteMapping("/customers/{id}")
	public void deleteCustomer(@PathVariable(name = "id") Long id) {
		 bankAccountService.deleteCustomer(id);
	}
}
