package med.sig.bank.repositeries;

import med.sig.bank.entities.BankAccount;
import med.sig.bank.entities.Customer;
import med.sig.bank.entities.SavingAccount;
import med.sig.bank.enums.AccountStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;


@DataJpaTest
class BankAccountRepositoryTest {

    @Autowired
    private BankAccountRepository bankAccountRepository;


    @Test
    void testSaveSavingAccount(){
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
        BankAccount savedSavingAccount = bankAccountRepository.save(savingAccount);
        Assertions.assertThat(savedSavingAccount).isEqualTo(savingAccount);
        Assertions.assertThat(savedSavingAccount.getBalance()).isEqualTo(savingAccount.getBalance());
        Assertions.assertThat(savedSavingAccount.getCreateAt()).isEqualTo(savingAccount.getCreateAt());
        Assertions.assertThat(((SavingAccount) savedSavingAccount).getInterestRate()).isEqualTo(savingAccount.getInterestRate());
        System.out.println("******************");
        System.out.println(savingAccount.getCustomer());
        System.out.println(savedSavingAccount.getCustomer());
        System.out.println("************************");
        //Assertions.assertThat(savedSavingAccount.getCustomer()).isEqualTo(customer);
    }

    @Test
    void testGetAccount(){

    }
}