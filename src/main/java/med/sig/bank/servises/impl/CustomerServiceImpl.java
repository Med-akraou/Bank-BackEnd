package med.sig.bank.servises.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import med.sig.bank.dtos.CustomerDTO;
import med.sig.bank.entities.Customer;
import med.sig.bank.exceptions.NotFoundCustomerException;
import med.sig.bank.mappers.BankMapper;
import med.sig.bank.repositeries.CustomerRepository;
import med.sig.bank.servises.CustomerService;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

	private CustomerRepository customerRepository;
	private BankMapper mapper;

	@Override
	public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
		log.info("saving new customer");
		Customer customer = mapper.toCustomer(customerDTO);
		return mapper.toCustomerDto(customerRepository.save(customer));
	}

	@Override
	public List<CustomerDTO> litCustomers() {
		List<Customer> customers = customerRepository.findAll();
		List<CustomerDTO> customersDTO = customers.stream().map(customer -> mapper.toCustomerDto(customer))
				.collect(Collectors.toList());
		return customersDTO;
	}

	@Override
	public CustomerDTO getCustomer(Long id) {
		Customer customer = customerRepository.findById(id)
				.orElseThrow(() -> new NotFoundCustomerException("Customer not found"));
		return mapper.toCustomerDto(customer);
	}

	@Override
	public CustomerDTO updateCostumer(CustomerDTO customerDto) {
		log.info("update user");

		Customer customer = customerRepository.findCustomerByEmail(customerDto.getEmail());

		customer.setEmail(customerDto.getEmail());
		customer.setPhone(customerDto.getPhone());
		return mapper.toCustomerDto(customerRepository.save(customer));
	}

	@Override
	public void deleteCustomer(Long id) {
		Customer customer = customerRepository.findById(id)
				.orElseThrow(() -> new NotFoundCustomerException("Customer not found"));
		customerRepository.delete(customer);

	}

}
