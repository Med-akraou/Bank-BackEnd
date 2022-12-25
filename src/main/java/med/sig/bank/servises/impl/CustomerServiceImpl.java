package med.sig.bank.servises.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import med.sig.bank.dtos.CustomerDTO;
import med.sig.bank.entities.Customer;
import med.sig.bank.exceptions.NotFoundCustomerException;
import med.sig.bank.mappers.BankMapper;
import med.sig.bank.repositeries.CustomerRepositery;
import med.sig.bank.servises.CustmorService;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustmorService {

	private CustomerRepositery customerRepositery;
	private BankMapper mapper;

	@Override
	public CustomerDTO saveCustomer(CustomerDTO costomerDTO) {
		log.info("saving new customer");
		Customer customer = customerRepositery.save(mapper.fromCustomerDTO(costomerDTO));
		return mapper.fromCustmor(customer);
	}

	@Override
	public List<CustomerDTO> litCustomers() {
		List<Customer> customers = customerRepositery.findAll();
		List<CustomerDTO> customersDTO = customers.stream().map(customer -> mapper.fromCustmor(customer))
				.collect(Collectors.toList());
		return customersDTO;
	}

	@Override
	public CustomerDTO getCustomer(Long id) {
		Customer customer = customerRepositery.findById(id)
				.orElseThrow(() -> new NotFoundCustomerException("Customer not found"));
		return mapper.fromCustmor(customer);
	}

	@Override
	public CustomerDTO updateCostumer(CustomerDTO cuDto) {
		log.info("update user");
		Customer customer = mapper.fromCustomerDTO(cuDto);
		return mapper.fromCustmor(customerRepositery.save(customer));
	}

	@Override
	public void deleteCustomer(Long id) {
		Customer customer = customerRepositery.findById(id)
				.orElseThrow(() -> new NotFoundCustomerException("Customer not found"));
		customerRepositery.delete(customer);

	}

}
