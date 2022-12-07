package med.sig.bank.repositeries;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import med.sig.bank.entities.BankAccount;
import med.sig.bank.entities.Operation;


public interface BankAccountRepositery extends JpaRepository<BankAccount,String> {
    
	Page<Operation> findByAccountIdOrderByOperationDateDesc(String acountId,Pageable pageable);
}
