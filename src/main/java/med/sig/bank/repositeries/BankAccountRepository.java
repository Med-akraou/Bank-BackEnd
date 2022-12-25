package med.sig.bank.repositeries;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import med.sig.bank.entities.BankAccount;
import med.sig.bank.entities.Operation;


public interface BankAccountRepository extends JpaRepository<BankAccount,String> {
    
}
