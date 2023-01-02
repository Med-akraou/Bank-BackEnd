package med.sig.bank.servises;

import java.util.List;

import med.sig.bank.dtos.CustomerDTO;

public interface CustomerService {

	CustomerDTO saveCustomer(CustomerDTO customerDTO);

	CustomerDTO getCustomer(String customerId);

	List<CustomerDTO> litCustomers();

	CustomerDTO updateCostumer(String customerId,CustomerDTO cuDto);

	void deleteCustomer(String customerId);

}
