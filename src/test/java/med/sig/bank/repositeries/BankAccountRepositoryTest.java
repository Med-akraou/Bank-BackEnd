package med.sig.bank.repositeries;

import med.sig.bank.entities.BankAccount;
import med.sig.bank.entities.CurrentAccount;
import med.sig.bank.entities.Customer;
import med.sig.bank.entities.SavingAccount;
import med.sig.bank.enums.AccountStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


@DataJpaTest
class BankAccountRepositoryTest {

    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private CustomerRepository customerRepository;


    @Test
    void shouldSaveSavingAccount(){
        Customer customer = new Customer();
        customer.setFirstname("Med");
        customer.setLastname("Med");
        customer.setEmail("med@gmail.com");
        customer.setPhone("+212687550987");
        SavingAccount savingAccount = new SavingAccount();
        savingAccount.setId("idSavingAccount");
        savingAccount.setBalance(30_000.50);
        savingAccount.setStatus(AccountStatus.CREATED);
        savingAccount.setCreateAt(new Date());
        savingAccount.setCustomer(customer);
        savingAccount.setInterestRate(23.23);
        customerRepository.save(customer);
        SavingAccount savedSavingAccount = bankAccountRepository.save(savingAccount);
        Assertions.assertThat(savedSavingAccount).isEqualTo(savingAccount);
        Assertions.assertThat(savedSavingAccount.getBalance()).isEqualTo(savingAccount.getBalance());
        Assertions.assertThat(savedSavingAccount.getCreateAt()).isEqualTo(savingAccount.getCreateAt());
        Assertions.assertThat(((SavingAccount) savedSavingAccount).getInterestRate()).isEqualTo(savingAccount.getInterestRate());
        Assertions.assertThat(savedSavingAccount.getCustomer()).isEqualTo(customer);

    }

    @Test
    void shouldSaveCurrentAccount(){
        Customer customer = new Customer();
        customer.setFirstname("Med");
        customer.setLastname("Med");
        customer.setEmail("med@gmail.com");
        customer.setPhone("+212687550987");
        CurrentAccount currentAccount = new CurrentAccount();
        currentAccount.setId("idCurrentAccount");
        currentAccount.setBalance(30_000.50);
        currentAccount.setStatus(AccountStatus.CREATED);
        currentAccount.setCreateAt(new Date());
        currentAccount.setCustomer(customer);
        currentAccount.setOverDraft(23.23);
        customerRepository.save(customer);
        CurrentAccount savedSavingAccount = bankAccountRepository.save(currentAccount);
        Assertions.assertThat(savedSavingAccount).isEqualTo(currentAccount);
        Assertions.assertThat(savedSavingAccount.getBalance()).isEqualTo(currentAccount.getBalance());
        Assertions.assertThat(savedSavingAccount.getCreateAt()).isEqualTo(currentAccount.getCreateAt());
        Assertions.assertThat(((CurrentAccount) currentAccount).getOverDraft()).isEqualTo(currentAccount.getOverDraft());
        Assertions.assertThat(savedSavingAccount.getCustomer()).isEqualTo(customer);

    }

    @Test
    void shouldReturnAccountAccount() {
        Customer customer = new Customer();
        customer.setFirstname("Med");
        customer.setLastname("Med");
        customer.setEmail("med@gmail.com");
        customer.setPhone("+212687550987");

        SavingAccount savingAccount = new SavingAccount();
        savingAccount.setId("idSavingAccount");
        savingAccount.setBalance(30_000.50);
        savingAccount.setStatus(AccountStatus.CREATED);
        savingAccount.setCreateAt(new Date());
        savingAccount.setCustomer(customerRepository.save(customer));
        savingAccount.setInterestRate(23.23);

        CurrentAccount currentAccount = new CurrentAccount();
        currentAccount.setId("idCurrentAccount");
        currentAccount.setBalance(30_000.50);
        currentAccount.setStatus(AccountStatus.CREATED);
        currentAccount.setCreateAt(new Date());
        currentAccount.setCustomer(customerRepository.save(customer));
        currentAccount.setOverDraft(23.23);

        SavingAccount retrievedSavingAccount = (SavingAccount) bankAccountRepository.findById(bankAccountRepository.save(savingAccount).getId()).get();
        CurrentAccount retrievedCurrentAccount = (CurrentAccount) bankAccountRepository.findById(bankAccountRepository.save(currentAccount).getId()).get();

        Assertions.assertThat(retrievedCurrentAccount).isEqualTo(currentAccount);
        Assertions.assertThat(retrievedSavingAccount).isEqualTo(savingAccount);
    }


}