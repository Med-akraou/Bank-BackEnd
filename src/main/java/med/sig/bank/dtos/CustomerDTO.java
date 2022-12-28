package med.sig.bank.dtos;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CustomerDTO {

	private String firstname;
    private String lastname;
    private String email;
    private String phone;
}
