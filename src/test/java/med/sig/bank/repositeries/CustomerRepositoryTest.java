package med.sig.bank.repositeries;

import med.sig.bank.entities.Customer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@DataJpaTest
class CustomerRepositoryTest {
    @Autowired
    CustomerRepository customerRepository;

    private Customer customer;

    @BeforeEach
    void setUp(){
        customer = new Customer();
        customer.setFirstname("Med");
        customer.setLastname("Med");
        customer.setEmail("med@gmail.com");
        customer.setPhone("+2126759943490");
        customer.setCustomerId("customer_id");
        customerRepository.deleteAll();
    }

    @Test
    void testSaveCustomer(){
        Customer savedCustomer = customerRepository.save(customer);
        System.out.println("**********************");
        savedCustomer.setFirstname("ddddddddddd");
        System.out.println("**********************");
        System.out.println(savedCustomer);
        System.out.println(customer);
        System.out.println("***********************");
        Assertions.assertThat(savedCustomer).isEqualTo(customer);
    }

    @Test
    void testRetrieveCustomerByCustomerId(){
        //given
        Customer savedCustomer = customerRepository.save(customer);
        //when
        String customerId = savedCustomer.getCustomerId();
        //then
        Assertions.assertThat(customerRepository.findCustomerByCustomerId(customerId)).isEqualTo(customer);
        Assertions.assertThat(customerRepository.findCustomerByCustomerId("fake_customer_id")).isNull();
    }

    @Test
    void testListCustomers(){
        List<Customer> customers = new ArrayList<>();
        Stream.of("Hassan Chafi","Iman Hossni","Farid Majidi").forEach(name->{
                    Customer customer = new Customer();
                    customer.setFirstname(name.split(" ")[0]);
                    customer.setLastname(name.split(" ")[1]);
                    customer.setEmail(name.replace(" ","")+"@gmail.com");
                    customer.setPhone("+212347850933");
                    customers.add(customerRepository.save(customer));
                }
        );
        Assertions.assertThat(customerRepository.findAll()).isEqualTo(customers);
    }

    @Test
    void testUpdateCustomer(){

        Customer savedCustomer = customerRepository.save(customer);
        String newPhone = "+212576767676";
        String newEmail = "newemail@gmail.com";
        savedCustomer.setPhone(newPhone);
        savedCustomer.setEmail(newEmail);
        customerRepository.save(savedCustomer);
        Customer updatedCustomer = customerRepository.findById(savedCustomer.getId()).get();
        Assertions.assertThat(updatedCustomer.getEmail()).isEqualTo(newEmail);
        Assertions.assertThat(updatedCustomer.getPhone()).isEqualTo(newPhone);
    }

    @Test
    void testDeleteCustomer(){
      customerRepository.save(customer);
      customerRepository.delete(customer);
      Assertions.assertThat(customerRepository.findById(customer.getId())).isEmpty();
    }

}