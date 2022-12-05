package med.sig.bank.repositeries;


import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepositery extends JpaRepository<med.sig.bank.entities.Operation,Long> {
    
}
