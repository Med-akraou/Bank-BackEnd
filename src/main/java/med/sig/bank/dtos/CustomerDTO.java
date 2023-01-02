package med.sig.bank.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class CustomerDTO {

	private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private String customerId;
}
