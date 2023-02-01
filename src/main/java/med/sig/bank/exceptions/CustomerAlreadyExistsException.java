package med.sig.bank.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CustomerAlreadyExistsException extends RuntimeException{
    private String message;

    public CustomerAlreadyExistsException(String message) {
        super(message);
    }
}
