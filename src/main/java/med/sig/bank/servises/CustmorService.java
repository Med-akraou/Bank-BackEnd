package med.sig.bank.servises;

import java.util.List;

import med.sig.bank.dtos.CustomerDTO;
import med.sig.bank.entities.CurrentAccount;
import med.sig.bank.entities.SavingAccount;

public interface CustmorService {

	CustomerDTO saveCustomer(CustomerDTO costomerDTO);

	CustomerDTO getCustomer(Long id);

	List<CustomerDTO> litCustomers();

	CustomerDTO updateCostumer(CustomerDTO cuDto);

	void deleteCustomer(Long id);

}
