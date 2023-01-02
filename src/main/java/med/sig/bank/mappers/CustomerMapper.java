package med.sig.bank.mappers;


import med.sig.bank.dtos.CustomerDTO;
import med.sig.bank.entities.Customer;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@org.mapstruct.Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerDTO toCustomerDto(Customer customer);
    Customer toCustomer(CustomerDTO customerDTO);
    List<CustomerDTO> toCustomerDtos(List<Customer> customers);
    List<Customer> toCustomers(List<CustomerDTO> customerDTOS);
    
}
