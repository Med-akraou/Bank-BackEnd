package med.sig.bank.repositeries;

import org.springframework.data.jpa.repository.JpaRepository;

import med.sig.bank.entities.Customer;


public interface CustomerRepositery extends JpaRepository<Customer, Long> {
    
}
