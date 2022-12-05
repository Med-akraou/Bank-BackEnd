package med.sig.bank.dtos;

import lombok.Data;

@Data
public class CustomerDTO {

	private Long id;
	private String firstname;
    private String lastname;
    private String email;
    private String phone;
}
