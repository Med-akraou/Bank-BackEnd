package med.sig.bank.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import med.sig.bank.dtos.CustomerDTO;
import med.sig.bank.servises.CustomerService;

@RestController
@AllArgsConstructor
@Slf4j
public class CustomerController {

	private CustomerService customerService;
	
	@PostMapping("/customers")
	public CustomerDTO addCustomer(@RequestBody CustomerDTO customerDTO) {
		return customerService.saveCustomer(customerDTO);
	}
	
	@GetMapping("/customers/{id}")
	public CustomerDTO getCustomer(@PathVariable(name = "id") Long id) {
		return customerService.getCustomer(id);
	}
	
	@GetMapping("/customers")
	public List<CustomerDTO> listCustomer(){
		return customerService.litCustomers();
	}
	
	@PutMapping("/customers")
	public CustomerDTO updateCustomer(@RequestBody CustomerDTO customerDTO) {
		return customerService.updateCostumer(customerDTO);
	}
	
	@DeleteMapping("/customers/{id}")
	public void deleteCustomer(@PathVariable(name = "id") Long id) {
		 customerService.deleteCustomer(id);
	}
}
