package med.sig.bank.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundCustomerException extends RuntimeException{

	private String message;
	public NotFoundCustomerException(String message) {
		super(message);
	}
	
	
}
