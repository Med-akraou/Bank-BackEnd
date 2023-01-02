package med.sig.bank.repositeries;

import org.springframework.data.jpa.repository.JpaRepository;

import med.sig.bank.entities.Customer;


public interface CustomerRepository extends JpaRepository<Customer, Long> {
    public Customer findCustomerByEmail(String email);
    public  Customer findCustomerByCustomerId(String customerId);
}
