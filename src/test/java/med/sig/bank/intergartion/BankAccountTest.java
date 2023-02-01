package med.sig.bank.intergartion;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import med.sig.bank.dtos.CurrentAccountRequest;
import med.sig.bank.dtos.OperationDTO;
import med.sig.bank.dtos.SavingAccountRequest;
import med.sig.bank.dtos.TransferDTO;
import med.sig.bank.entities.CurrentAccount;
import med.sig.bank.entities.Customer;
import med.sig.bank.entities.SavingAccount;
import med.sig.bank.enums.AccountStatus;
import med.sig.bank.repositeries.BankAccountRepository;
import med.sig.bank.repositeries.CustomerRepository;
import med.sig.bank.repositeries.OperationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BankAccountTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BankAccountRepository bankAccountRepository;

    @Autowired
    OperationRepository operationRepository;

    private ObjectMapper jsonMapper = new ObjectMapper();
    private final static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    @BeforeEach
    void setUp(){
        bankAccountRepository.deleteAll();
    }

    @Test
    @Transactional
    void shouldCreateCurrentAccount() throws Exception {
        Customer customer = new Customer();
        customer.setFirstname("Med");
        customer.setLastname("Med");
        customer.setCustomerId("customerId1");
        customer.setEmail("med@gmail.com");
        customer.setPhone("+212673536353");
        customerRepository.save(customer);
        CurrentAccountRequest currentAccountRequest = new CurrentAccountRequest();
        currentAccountRequest.setBalance(38_000.5);
        currentAccountRequest.setCustomerId(customer.getCustomerId());
        currentAccountRequest.setOverDraft(3.2);

        mockMvc.perform(post("/saveCurrentAccount").contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(currentAccountRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("balance").value(currentAccountRequest.getBalance()))
                .andExpect(jsonPath("status").value("CREATED"))
                .andExpect(jsonPath("overDraft").value(currentAccountRequest.getOverDraft()))
                .andExpect(jsonPath("type").value("CurrentAccount"))
                .andExpect(jsonPath("customerDTO.firstname").value(customer.getFirstname()))
                .andExpect(jsonPath("customerDTO.lastname").value(customer.getLastname()))
                .andExpect(jsonPath("customerDTO.email").value(customer.getEmail()));

    }

    @Test
    @Transactional
    void shouldThrowNotFountCustomerException() throws Exception {
        CurrentAccountRequest currentAccountRequest = new CurrentAccountRequest();
        currentAccountRequest.setBalance(38_000.5);
        currentAccountRequest.setCustomerId("notExistingCustomerId");
        currentAccountRequest.setOverDraft(3.2);

        mockMvc.perform(post("/saveCurrentAccount").contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(currentAccountRequest)))
                .andExpect(status().isNotFound())
                ;
    }

    @Test
    @Transactional
    void shouldCreateSavingAccount() throws Exception {
        Customer customer = new Customer();
        customer.setFirstname("Med");
        customer.setLastname("Med");
        customer.setCustomerId("customerId2");
        customer.setEmail("med@gmail.com");
        customer.setPhone("+212673536353");
        customerRepository.save(customer);
        SavingAccountRequest savingAccountRequest = new SavingAccountRequest();
        savingAccountRequest.setBalance(38_000.5);
        savingAccountRequest.setCustomerId(customer.getCustomerId());
        savingAccountRequest.setInterestRate(3.2);

        mockMvc.perform(post("/saveSavingAccount").contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(savingAccountRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("balance").value(savingAccountRequest.getBalance()))
                .andExpect(jsonPath("status").value("CREATED"))
                .andExpect(jsonPath("interestRate").value(savingAccountRequest.getInterestRate()))
                .andExpect(jsonPath("type").value("SavingAccount"))
                .andExpect(jsonPath("customerDTO.firstname").value(customer.getFirstname()))
                .andExpect(jsonPath("customerDTO.lastname").value(customer.getLastname()))
                .andExpect(jsonPath("customerDTO.email").value(customer.getEmail()));

    }

        @Test
        @Transactional
       void shouldReturnCurrentAccount() throws Exception {
           Customer customer = new Customer();
           customer.setFirstname("Med");
           customer.setLastname("Med");
           customer.setCustomerId("customerId3");
           customer.setEmail("med@gmail.com");

           CurrentAccount account = new CurrentAccount();
           account.setId("accountId1");
           account.setOverDraft(3.3);
           account.setBalance(3000);
           account.setStatus(AccountStatus.CREATED);
           Date creatAt = new Date();
           account.setCreateAt(creatAt);
           account.setCustomer(customerRepository.save(customer));
           bankAccountRepository.save(account);

           mockMvc.perform(get("/accounts/{id}",account.getId()))
                   .andExpect(status().isOk())
                   .andExpect(jsonPath("balance").value(account.getBalance()))
                   .andExpect(jsonPath("status").value("CREATED"))
                   //.andExpect(jsonPath("createAt").value(creatAt.toString()))
                   .andExpect(jsonPath("overDraft").value(account.getOverDraft()))
                   .andExpect(jsonPath("type").value("CurrentAccount"))
                   .andExpect(jsonPath("customerDTO.firstname").value(customer.getFirstname()))
                   .andExpect(jsonPath("customerDTO.lastname").value(customer.getLastname()))
                   .andExpect(jsonPath("customerDTO.email").value(customer.getEmail()));
           ;

       }

    @Test
    @Transactional
    void shouldReturnSavingAccount() throws Exception {
        Customer customer = new Customer();
        customer.setFirstname("Med");
        customer.setLastname("Med");
        customer.setCustomerId("customerId3");
        customer.setEmail("med@gmail.com");

        SavingAccount account = new SavingAccount();
        account.setId("accountId2");
        account.setInterestRate(3.3);
        account.setBalance(3000);
        account.setStatus(AccountStatus.CREATED);
        Date creatAt = new Date();
        account.setCreateAt(creatAt);
        account.setCustomer(customerRepository.save(customer));
        bankAccountRepository.save(account);

        mockMvc.perform(get("/accounts/{id}",account.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("balance").value(account.getBalance()))
                .andExpect(jsonPath("status").value("CREATED"))
                //.andExpect(jsonPath("createAt").value(formatter.format(creatAt)))
                .andExpect(jsonPath("interestRate").value(account.getInterestRate()))
                .andExpect(jsonPath("type").value("SavingAccount"))
                .andExpect(jsonPath("customerDTO.firstname").value(customer.getFirstname()))
                .andExpect(jsonPath("customerDTO.lastname").value(customer.getLastname()))
                .andExpect(jsonPath("customerDTO.email").value(customer.getEmail()));
        ;

    }

    @Test
    @Transactional
    void shouldThrowNotFountAccountException() throws Exception {
        mockMvc.perform(get("/accounts/{id}","fake-account-id").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertThat(result.getResolvedException().getMessage()).isEqualTo("Account not found"));
        ;
    }

    @Test
    @Transactional
    void shouldReturnAllAccounts() throws Exception {
        Customer customer = new Customer();
        customer.setFirstname("Med");
        customer.setLastname("Med");
        customer.setCustomerId("customerId4");
        customer.setEmail("med@gmail.com");

        CurrentAccount account = new CurrentAccount();
        account.setId("accountId-1");
        account.setOverDraft(3.3);
        account.setBalance(3000);
        account.setStatus(AccountStatus.CREATED);
        Date creatAt = new Date();
        account.setCreateAt(creatAt);
        account.setCustomer(customerRepository.save(customer));
        bankAccountRepository.save(account);

        SavingAccount account1 = new SavingAccount();
        account1.setId("accountId-2");
        account1.setInterestRate(3.3);
        account1.setBalance(4000);
        account1.setStatus(AccountStatus.CREATED);
        Date creatAtt = new Date();
        account1.setCreateAt(creatAtt);
        account1.setCustomer(customerRepository.save(customer));
        bankAccountRepository.save(account1);

        mockMvc.perform(MockMvcRequestBuilders.get("/accounts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].status").value("CREATED"))
                .andExpect(jsonPath("$[0].balance", is(account.getBalance())))
                .andExpect(jsonPath("$[0].overDraft", is(account.getOverDraft())))
                .andExpect(jsonPath("$[0].customerDTO.email", is(customer.getEmail())))

                .andExpect(jsonPath("$[1].status").value("CREATED"))
                .andExpect(jsonPath("$[1].balance", is(account1.getBalance())))
                .andExpect(jsonPath("$[1].interestRate", is(account1.getInterestRate())))
                .andExpect(jsonPath("$[1].customerDTO.firstname", is(customer.getFirstname())))
             ;

    }

    @Test
    void shouldDebitAccount() throws Exception {

        CurrentAccount account = new CurrentAccount();
        account.setId("accountId-to-debit");
        account.setOverDraft(3.3);
        account.setBalance(3000);
        account.setStatus(AccountStatus.ACTIVATED);
        Date creatAt = new Date();
        account.setCreateAt(creatAt);
        bankAccountRepository.save(account);

        OperationDTO op = new OperationDTO();
        op.setAccountId(account.getId());
        op.setAmount(1000);
        op.setDescription("Debit operation test");

        mockMvc.perform(post("/accounts/debit").contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(op)))
                .andExpect(status().isOk());
        assertThat(bankAccountRepository.findById(account.getId()).get().getBalance()).isEqualTo(account.getBalance()-op.getAmount());
    }

    @Test
    void shouldThrowAccountNotActivatedException() throws Exception {
        CurrentAccount account = new CurrentAccount();
        account.setId("accountId-to-debit");
        account.setOverDraft(3.3);
        account.setBalance(3000);
        account.setStatus(AccountStatus.CREATED);
        Date creatAt = new Date();
        account.setCreateAt(creatAt);
        bankAccountRepository.save(account);

        OperationDTO op = new OperationDTO();
        op.setAccountId(account.getId());
        op.setAmount(1000);
        op.setDescription("Debit operation test");

        mockMvc.perform(post("/accounts/debit").contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(op)))
                .andExpect(status().isBadRequest())
         .andExpect(result -> assertThat(result.getResolvedException().getMessage()).isEqualTo("This account is not activated yet"));
    }

    @Test
    void shouldThrowBalanceNotSufficientException() throws Exception {
        CurrentAccount account = new CurrentAccount();
        account.setId("accountId-debit");
        account.setOverDraft(3.3);
        account.setStatus(AccountStatus.ACTIVATED);
        account.setBalance(3000);
        bankAccountRepository.save(account);

        OperationDTO op = new OperationDTO();
        op.setAccountId(account.getId());
        op.setAmount(3001);

        mockMvc.perform(post("/accounts/debit").contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(op)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertThat(result.getResolvedException().getMessage()).isEqualTo("Balance not sufficient"));

    }

    @Test
    void shouldCreditAccount() throws Exception {
        CurrentAccount account = new CurrentAccount();
        account.setId("accountId-to-credit");
        account.setOverDraft(3.3);
        account.setBalance(3000);
        account.setStatus(AccountStatus.ACTIVATED);
        Date creatAt = new Date();
        account.setCreateAt(creatAt);
        bankAccountRepository.save(account);

        OperationDTO op = new OperationDTO();
        op.setAccountId(account.getId());
        op.setAmount(1000);
        op.setDescription("Credit operation test");

        mockMvc.perform(post("/accounts/credit").contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(op)))
                .andExpect(status().isOk());
        assertThat(bankAccountRepository.findById(account.getId()).get().getBalance()).isEqualTo(account.getBalance()+op.getAmount());
    }

    @Test
    void shouldTransferFromAccountToOther() throws Exception {
        CurrentAccount sourceAccount = new CurrentAccount();
        sourceAccount.setId("accountId-source");
        sourceAccount.setOverDraft(3.3);
        sourceAccount.setBalance(3000);
        sourceAccount.setStatus(AccountStatus.ACTIVATED);
        bankAccountRepository.save(sourceAccount);

        SavingAccount destinationAccount = new SavingAccount();
        destinationAccount.setId("accountId-destination");
        destinationAccount.setInterestRate(3.3);
        destinationAccount.setBalance(3000);
        destinationAccount.setStatus(AccountStatus.ACTIVATED);
        bankAccountRepository.save(destinationAccount);


        TransferDTO transferDTO = new TransferDTO();
        transferDTO.setAccountSource(sourceAccount.getId());
        transferDTO.setAccountDestination(destinationAccount.getId());
        transferDTO.setAmount(1500);
        transferDTO.setDescription("transfer test");

        mockMvc.perform(post("/accounts/transfer").contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(transferDTO)))
                .andExpect(status().isOk());
        assertThat(bankAccountRepository.findById("accountId-source").get().getBalance()).isEqualTo(1500);
        assertThat(bankAccountRepository.findById("accountId-destination").get().getBalance()).isEqualTo(4500);
    }

}
