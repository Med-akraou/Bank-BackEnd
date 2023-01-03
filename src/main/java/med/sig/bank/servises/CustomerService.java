package med.sig.bank.servises;

import java.util.List;

import med.sig.bank.dtos.CustomerDTO;

public interface CustomerService {
	/**
	 * Add new Customer
	 * @param customerDTO customer to save
	 * @return the saved customer
	 */
	CustomerDTO saveCustomer(CustomerDTO customerDTO);

	/**
	 * Get customer by id
	 * @param customerId customer id
	 * @return the customer if exist, throws NotFoundCustomerException if not exist
	 */
	CustomerDTO getCustomer(String customerId);

	/**
	 *
	 * @return all customers
	 */
	List<CustomerDTO> litCustomers();

	/**
	 * Update customer
	 * @param customerId customer id to update
	 * @param cuDto new customer information
	 * @return the updated customer
	 */
	CustomerDTO updateCostumer(String customerId,CustomerDTO cuDto);

	/**
	 * Delete customer by id
	 * @param customerId customer id to delete
	 */
	void deleteCustomer(String customerId);

}
