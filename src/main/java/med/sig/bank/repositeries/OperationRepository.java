package med.sig.bank.repositeries;


import med.sig.bank.entities.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<med.sig.bank.entities.Operation,Long> {
    Page<Operation> findByAccountIdOrderByOperationDateDesc(String accountId, Pageable pageable);

}
