package med.sig.bank.servises.impl;

import lombok.extern.slf4j.Slf4j;
import med.sig.bank.dtos.CustomerDTO;
import med.sig.bank.entities.Customer;
import med.sig.bank.mappers.BankMapper;
import med.sig.bank.repositeries.CustomerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;


@ExtendWith(MockitoExtension.class)
//@MockitoSettings(strictness = Strictness.LENIENT)
@Slf4j
//@SpringBootTest
class CustomerServiceImplTest {

    @Mock
    CustomerRepository customerRepository;

    @Mock
    BankMapper bankMapper;

    @InjectMocks
    CustomerServiceImpl customerService;


    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Should create a new Department")
    void testSaveCustomer() {
        CustomerDTO customerDto = new CustomerDTO();
        customerDto.setLastname("Med");
        customerDto.setFirstname("Med");
        customerDto.setEmail("med@gmail.com");
        customerDto.setPhone("+212587498733");

        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDto,customer);

        Mockito.when(bankMapper.toCustomer(customerDto)).thenReturn(customer);
        Mockito.when(bankMapper.toCustomerDto(any())).thenReturn(customerDto);

        CustomerDTO savedCustomerDto = customerService.saveCustomer(customerDto);
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
        Mockito.verify(customerRepository).save(customerArgumentCaptor.capture());

        Assertions.assertThat(customerArgumentCaptor.getValue()).isEqualTo(customer);
        Assertions.assertThat(savedCustomerDto).isEqualTo(customerDto);
    }

    @Test
    void getCustomer() {
        CustomerDTO customerDto = new CustomerDTO();
        customerDto.setLastname("Med");
        customerDto.setFirstname("Med");
        customerDto.setEmail("med@gmail.com");
        customerDto.setPhone("+212587498733");

        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDto,customer);

        Mockito.when(bankMapper.toCustomerDto(any())).thenReturn(customerDto);

        BDDMockito.given(customerRepository.findById(1L)).willReturn(Optional.of(customer));
        CustomerDTO receivedCustomerDto= customerService.getCustomer(1L);
        Mockito.verify(customerRepository).findById(1L);

        Assertions.assertThat(receivedCustomerDto).isEqualTo(customerDto);
     }


    @Test
    void testLitCustomers() {
        List<CustomerDTO> custmors = new ArrayList<>();
        Stream.of("Hassan Chafi","Iman Hossni","Farid Majidi").forEach(name->{
                    CustomerDTO custmor = new CustomerDTO();
                    custmor.setFirstname(name.split(" ")[0]);
                    custmor.setLastname(name.split(" ")[1]);
                    custmor.setEmail(name.replace(" ","")+"@gmail.com");
                    custmor.setPhone("+212347850933");
                    CustomerDTO savedCustemor = customerService.saveCustomer(custmor);
                    custmors.add(savedCustemor);
                }
                );


        Mockito.when(customerService.litCustomers()).thenReturn(custmors);
        List<CustomerDTO> expectedReturn = customerService.litCustomers();
        Mockito.verify(customerRepository).findAll();
        Assertions.assertThat(custmors.equals(expectedReturn)).isTrue();
    }



    @Test
    void updateCostumer() {
    }

    @Test
    void deleteCustomer() {
    }
}