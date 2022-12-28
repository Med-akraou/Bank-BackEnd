package med.sig.bank.repositeries;

import med.sig.bank.entities.Customer;
import org.assertj.core.api.Assertions;
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



    @Test
    void testSaveCustomer(){
        Customer customer = new Customer();
        customer.setFirstname("Med");
        customer.setLastname("Med");
        customer.setEmail("med@gmail.com");
        customer.setPhone("+2126759943490");
        Customer savedCustomer = customerRepository.save(customer);
        Assertions.assertThat(savedCustomer).isEqualTo(customer);
    }

    @Test
    void testRetrieveCustomerByEmail(){
        String email = "med@gmail.com";
        Customer customer = new Customer();
        customer.setFirstname("Med");
        customer.setLastname("Med");
        customer.setEmail(email);
        customer.setPhone("+2126759943490");
        customerRepository.save(customer);
        Assertions.assertThat(customerRepository.findCustomerByEmail(email)).isEqualTo(customer);
        Assertions.assertThat(customerRepository.findCustomerByEmail("fakeemail@gmail.Com")).isNull();

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
        String email = "med@gmail.com";
        Customer customer = new Customer();
        customer.setFirstname("Med");
        customer.setLastname("Med");
        customer.setEmail(email);
        customer.setPhone("+2126759943490");
        customerRepository.save(customer);
        String newPhone = "+212576767676";
        String newEmail = "newemail@gmail.com";
        customer.setPhone(newPhone);
        customer.setEmail(newEmail);
        customerRepository.save(customer);
        Customer updatedCustomer = customerRepository.findById(customer.getId()).get();
        Assertions.assertThat(updatedCustomer.getEmail()).isEqualTo(newEmail);
        Assertions.assertThat(updatedCustomer.getPhone()).isEqualTo(newPhone);
    }

    @Test
    void testDeleteCustomer(){
      Customer customer = new Customer();
      customer.setFirstname("Med");
      customer.setLastname("Med");
      customer.setEmail("med@gmail.com");
      customer.setPhone("+2126759943490");
      customerRepository.save(customer);
      customerRepository.deleteById(customer.getId());
      Assertions.assertThat(customerRepository.findById(customer.getId())).isEmpty();
    }

}