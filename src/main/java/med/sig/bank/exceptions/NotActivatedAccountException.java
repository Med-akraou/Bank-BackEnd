package med.sig.bank.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotActivatedAccountException extends RuntimeException {
    public NotActivatedAccountException(String s) {
        super(s);
    }
}
