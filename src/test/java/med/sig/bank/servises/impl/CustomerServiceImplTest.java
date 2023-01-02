package med.sig.bank.servises.impl;

import lombok.extern.slf4j.Slf4j;
import med.sig.bank.dtos.CustomerDTO;
import med.sig.bank.entities.Customer;
import med.sig.bank.exceptions.NotFoundCustomerException;
import med.sig.bank.mappers.CustomerMapper;
import med.sig.bank.repositeries.CustomerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;


@ExtendWith(MockitoExtension.class)
@Slf4j
class CustomerServiceImplTest {

    @Mock
    CustomerRepository customerRepository;

    @Mock
    CustomerMapper customerMapper;

    @InjectMocks
    CustomerServiceImpl customerService;

    private CustomerDTO customerDTO;


    @BeforeEach
    void setUp() {
        customerDTO = new CustomerDTO();
        customerDTO.setLastname("Med");
        customerDTO.setFirstname("Med");
        customerDTO.setEmail("med@gmail.com");
        customerDTO.setPhone("+212587498733");
    }

    @Test
    @DisplayName("Should create a new Department")
    void testSaveCustomer() {
        //given
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO,customer);

        Mockito.when(customerMapper.toCustomer(customerDTO)).thenReturn(customer);
        Mockito.when(customerMapper.toCustomerDto(any())).thenReturn(customerDTO);

        //when
        CustomerDTO savedCustomerDto = customerService.saveCustomer(customerDTO);
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
        Mockito.verify(customerRepository).save(customerArgumentCaptor.capture());
        //then
        Assertions.assertThat(customerArgumentCaptor.getValue()).isEqualTo(customer);
        Assertions.assertThat(savedCustomerDto).isEqualTo(customerDTO);
    }

    @Test
    @Disabled
    void getCustomer() {

        //given
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO,customer);

        Mockito.when(customerMapper.toCustomerDto(any())).thenReturn(customerDTO);
        BDDMockito.given(customerRepository.findCustomerByCustomerId("customerId_example")).willReturn(customer);

        //when
        CustomerDTO receivedCustomerDto= customerService.getCustomer("customerId_example");
        Mockito.verify(customerRepository).findCustomerByCustomerId("customerId_example");
        //then
        Assertions.assertThat(receivedCustomerDto).isEqualTo(customerDTO);
        Assertions.assertThatThrownBy(()->customerService.getCustomer("fake_customerId"))
                .isInstanceOf(NotFoundCustomerException.class)
                .hasMessageContaining("Customer not found");

     }


    @Test
    void testListCustomers() {
        //given
        List<CustomerDTO> customersDto = new ArrayList<>();
        List<Customer> customers = new ArrayList<>();
        Stream.of("Hassan Chafi","Iman Hossni","Farid Majidi").forEach(name->{
                    CustomerDTO customerDto = new CustomerDTO();
                    customerDto.setFirstname(name.split(" ")[0]);
                    customerDto.setLastname(name.split(" ")[1]);
                    customerDto.setEmail(name.replace(" ","")+"@gmail.com");
                    customerDto.setPhone("+212347850933");
                    Customer customer = new Customer();
                    BeanUtils.copyProperties(customerDto,customer);
                    customersDto.add(customerDto);
                    customers.add(customer);

                }
                );

        BDDMockito.given(customerRepository.findAll()).willReturn(customers);
        Mockito.when(customerMapper.toCustomerDtos(any())).thenReturn(customersDto);
        //when
        List<CustomerDTO> receivedCustomers = customerService.litCustomers();
        //then
        Mockito.verify(customerRepository).findAll();
        Assertions.assertThat(receivedCustomers).isEqualTo(customersDto);

    }



    @Test
    void updateCostumer() {
        //given
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO,customer);

        Mockito.when(customerMapper.toCustomerDto(any())).thenReturn(customerDTO);
        BDDMockito.given(customerRepository.findCustomerByCustomerId("customer_id")).willReturn(customer);

        //when
        CustomerDTO updatedCustomer = customerService.updateCostumer("customer_id",customerDTO);
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);

        //then
        Mockito.verify(customerRepository).findCustomerByCustomerId("customer_id");
        Mockito.verify(customerRepository).save(customerArgumentCaptor.capture());
        Assertions.assertThat(customerArgumentCaptor.getValue()).isEqualTo(customer);
        Assertions.assertThat(updatedCustomer).isEqualTo(customerDTO);
        Assertions.assertThatThrownBy(()->customerService.updateCostumer("fake_customerId",customerDTO))
                .isInstanceOf(NotFoundCustomerException.class)
                .hasMessageContaining("Customer not found");
    }

    @Test
    @Disabled
    void deleteCustomer() {
        //given
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO,customer);

        BDDMockito.given(customerRepository.findCustomerByCustomerId("customer_id")).willReturn(customer);
        //when
        customerService.deleteCustomer("customer_id");
        //then
        Mockito.verify(customerRepository).findCustomerByCustomerId(any());
        Mockito.verify(customerRepository).delete(any());
        Assertions.assertThatThrownBy(()->customerService.deleteCustomer("fake_customerId"))
                .isInstanceOf(NotFoundCustomerException.class)
                .hasMessageContaining("Customer not found");
    }
}