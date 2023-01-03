package med.sig.bank.servises.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import med.sig.bank.dtos.CustomerDTO;
import med.sig.bank.entities.Customer;
import med.sig.bank.exceptions.NotFoundCustomerException;
import med.sig.bank.mappers.CustomerMapper;
import med.sig.bank.repositeries.CustomerRepository;
import med.sig.bank.servises.CustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

	final private CustomerRepository customerRepository;

	final private CustomerMapper customerMapper;

	/**
	 * Save customer
	 * @param customerDTO Customer Dto
	 * @return the saved customer
	 */
	@Override
	public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
		customerDTO.setCustomerId(UUID.randomUUID().toString());
		log.info("saving new customer {}", customerDTO);
		Customer customer = customerMapper.toCustomer(customerDTO);
		return customerMapper.toCustomerDto(customerRepository.save(customer));
	}


	@Override
	public List<CustomerDTO> litCustomers() {
		log.info("Get customers list");
		List<Customer> customers = customerRepository.findAll();
		List<CustomerDTO> customersDTO = customerMapper.toCustomerDtos(customers);
		return customersDTO;
	}

	@Override
	public CustomerDTO getCustomer(String customerId) {
		Customer customer = customerRepository.findCustomerByCustomerId(customerId);
		if (customer == null){
			log.warn("Customer with {} does not exist",customerId);
			throw new NotFoundCustomerException("Customer not found");
		}
		log.info("get customer with id {}",customerId);
		return customerMapper.toCustomerDto(customer);
	}

	@Override
	public CustomerDTO updateCostumer(String customerId, CustomerDTO customerDto) {
		log.info("update user");
		Customer customer = customerRepository.findCustomerByCustomerId(customerId);
		if (customer == null){
			log.warn("Customer with {} does not exist",customerId);
			throw new NotFoundCustomerException("Customer not found");
		}
		BeanUtils.copyProperties(customerDto,customer);
		customer.setCustomerId(customerId);
		return customerMapper.toCustomerDto(customerRepository.save(customer));
	}

	@Override
	public void deleteCustomer(String customerId) {
		Customer customer = customerRepository.findCustomerByCustomerId(customerId);
		if (customer == null){
			log.warn("you are trying to delete a customer that does not exist");
			throw new NotFoundCustomerException("Customer not found");
		}
		log.info("delete customer {} ",customer);
		customerRepository.delete(customer);
	}

}
