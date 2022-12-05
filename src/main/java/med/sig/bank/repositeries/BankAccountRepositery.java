package med.sig.bank.repositeries;

import org.springframework.data.jpa.repository.JpaRepository;

import med.sig.bank.entities.BankAccount;


public interface BankAccountRepositery extends JpaRepository<BankAccount,String> {
    
}
