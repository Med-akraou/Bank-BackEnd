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
	
	@GetMapping("/customers/{customerId}")
	public CustomerDTO getCustomer(@PathVariable(name = "customerId") String customerId) {
		return customerService.getCustomer(customerId);
	}
	
	@GetMapping("/customers")
	public List<CustomerDTO> listCustomer(){
		return customerService.litCustomers();
	}
	
	@PutMapping("/customers/{customerId}")
	public CustomerDTO updateCustomer(@PathVariable(name = "customerId") String customerId , @RequestBody CustomerDTO customerDTO) {
		return customerService.updateCostumer(customerId,customerDTO);
	}
	
	@DeleteMapping("/customers/{customerId}")
	public void deleteCustomer(@PathVariable(name = "customerId") String customerId) {
		 customerService.deleteCustomer(customerId);
	}
}
