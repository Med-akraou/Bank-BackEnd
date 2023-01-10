package med.sig.bank.intergartion;

import com.fasterxml.jackson.databind.ObjectMapper;
import med.sig.bank.dtos.CustomerDTO;
import med.sig.bank.repositeries.CustomerRepository;
import med.sig.bank.servises.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerTest {

   @Autowired
   MockMvc mockMvc;

   @Autowired
   CustomerRepository customerRepository;

   @Autowired
    CustomerService customerService;

   private CustomerDTO customerDTO;

   private ObjectMapper jsonMapper = new ObjectMapper();

   @BeforeEach
   void setUp(){
       customerDTO = new CustomerDTO();
       customerDTO.setLastname("Med");
       customerDTO.setFirstname("Med");
       customerDTO.setEmail("med@gmail.com");
       customerDTO.setPhone("+212587498733");
   }

    @Test
    void shouldAddNewCustomer() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/customers").contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(customerDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("firstname").value("Med"))
                .andExpect(MockMvcResultMatchers.jsonPath("lastname").value("Med"))
                .andExpect(MockMvcResultMatchers.jsonPath("email").value("med@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("phone").value("+212587498733"));
    }

    @Test
    void shouldReturnCustomer() throws Exception {
       String customerId = customerService.saveCustomer(customerDTO).getCustomerId();

        mockMvc.perform(MockMvcRequestBuilders.get("/customers/{customerId}",customerId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("firstname").value(customerDTO.getFirstname()))
                .andExpect(MockMvcResultMatchers.jsonPath("lastname").value(customerDTO.getLastname()))
                .andExpect(MockMvcResultMatchers.jsonPath("email").value(customerDTO.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("phone").value(customerDTO.getPhone()))
                ;
    }

    @Test
    void shouldThrowNotFoundCustomer() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/customers/{customerId}","fake-customer-id"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
        ;
    }

    @Test
    void shouldReturn_listCustomers() throws Exception {
       customerService.saveCustomer(customerDTO);
       CustomerDTO other = new CustomerDTO();
       other.setFirstname("Ahmad");
       other.setLastname("Ahmad");
       other.setEmail("ahmad@gmail.com");
       other.setPhone("+212677777777");
       customerService.saveCustomer(other);
        mockMvc.perform(MockMvcRequestBuilders.get("/customers"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].firstname", is(customerDTO.getFirstname())))
                .andExpect(jsonPath("$[0].lastname", is(customerDTO.getLastname())))
                .andExpect(jsonPath("$[0].email", is(customerDTO.getEmail())))
                .andExpect(jsonPath("$[0].phone", is(customerDTO.getPhone())))
                .andExpect(jsonPath("$[0].customerId", is(customerDTO.getCustomerId())))

                .andExpect(jsonPath("$[1].firstname", is(other.getFirstname())))
                .andExpect(jsonPath("$[1].lastname", is(other.getLastname())))
                .andExpect(jsonPath("$[1].email", is(other.getEmail())))
                .andExpect(jsonPath("$[1].phone", is(other.getPhone())))
                .andExpect(jsonPath("$[1].customerId", is(other.getCustomerId())))
                ;

    }

    @Test
    void shouldUpdateCustomer() throws Exception {
       //given
       String customerId = customerService.saveCustomer(customerDTO).getCustomerId();
       customerDTO.setFirstname("NewName");
       customerDTO.setEmail("newemail@gmail.Com");
       customerDTO.setPhone("+2126789832993");

       //when then
        mockMvc.perform(MockMvcRequestBuilders.put("/customers/{customerId}",customerId).contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(customerDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("firstname").value(customerDTO.getFirstname()))
                .andExpect(MockMvcResultMatchers.jsonPath("email").value(customerDTO.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("phone").value(customerDTO.getPhone()));

    }

    @Test
    void shouldDeleteCostumer() throws Exception {
        String customerId = customerService.saveCustomer(customerDTO).getCustomerId();
        mockMvc.perform(MockMvcRequestBuilders.delete("/customers/{customerId}",customerId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldReturn_notFoundCustomer() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/customers/{customerId}","fake-customerId"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
