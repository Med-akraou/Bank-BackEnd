package med.sig.bank;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import med.sig.bank.entities.BankAccount;
import med.sig.bank.entities.CurrentAccount;
import med.sig.bank.entities.Customer;
import med.sig.bank.entities.Operation;
import med.sig.bank.entities.SavingAccount;
import med.sig.bank.enums.AccountStatus;
import med.sig.bank.enums.OperationType;
import med.sig.bank.repositeries.BankAccountRepositery;
import med.sig.bank.repositeries.CustomerRepositery;
import med.sig.bank.repositeries.OperationRepositery;

@SpringBootApplication
public class BankApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankApplication.class, args);
	}
	
	//@Bean
	CommandLineRunner start(BankAccountRepositery accountRepositery) {
		return args ->{
			BankAccount acc = accountRepositery.findById("679cecf7-0709-499b-beac-1c2aec6f7c21").orElse(null);
			System.out.println("*************************");
			System.out.println(acc.getId());
			System.out.println(acc.getBalance());
			System.out.println(acc.getStatus());
			System.out.println(acc.getCreateAt());
			System.out.println(acc.getCustomer().getEmail());
			if(acc instanceof CurrentAccount)
				System.out.println(((CurrentAccount)acc).getOverDraft());
			else if(acc instanceof SavingAccount)
				System.out.println(((SavingAccount)acc).getInterestRate());
			System.out.println(acc.getClass().getSimpleName());
		};
	}
	@Bean
	CommandLineRunner start(CustomerRepositery customerRepositery, BankAccountRepositery accountRepositery, OperationRepositery operationRepositery) {
		return args ->{
			Stream.of("Hassan Chafi","Iman Hossni","Farid Majidi").forEach(name -> {
				Customer c = new Customer();
				c.setFirstname(name.split(" ")[0]);
				c.setLastname(name.split(" ")[1]);
				c.setEmail(name.replace(" ", "")+"@gmail.com");
				c.setPhone("+212678932578");
				customerRepositery.save(c);
			});
		customerRepositery.findAll().forEach(custmer->{
			CurrentAccount currentAccount = new CurrentAccount();
			currentAccount.setId(UUID.randomUUID().toString());
			currentAccount.setBalance(Math.random()*90000);
			currentAccount.setCreateAt(new Date());
			currentAccount.setStatus(AccountStatus.CREATED);
			currentAccount.setCustomer(custmer);
			currentAccount.setOverDraft(9000);
			accountRepositery.save(currentAccount);
			
			SavingAccount savingAccount = new SavingAccount();
			savingAccount.setId(UUID.randomUUID().toString());
			savingAccount.setBalance(Math.random()*90000);
			savingAccount.setCreateAt(new Date());
			savingAccount.setStatus(AccountStatus.CREATED);
			savingAccount.setCustomer(custmer);
			savingAccount.setInterestRate(5.5);
			accountRepositery.save(savingAccount);
		});
		
		accountRepositery.findAll().forEach(account -> {
			for (int i=0;i<10;i++) {
				Operation operation = new Operation();
				operation.setOperationDate(new Date());
				operation.setAmount(Math.random()*1000);
				operation.setType(Math.random()>0.5? OperationType.DEBIT:OperationType.CREDIT);
				operation.setAccount(account);
				operationRepositery.save(operation);
			}
			
		});
		
		};
	}

}
