package med.sig.bank.controllers;

import med.sig.bank.dtos.CustomerDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CustomerControllerTest {


    @Autowired
    private MockMvc mockMvc;


    @Test
    void addCustomer() throws Exception {
        CustomerDTO customer = new CustomerDTO();
        customer.setLastname("Med");
        customer.setFirstname("Med");
        customer.setEmail("med@gmail.com");
        customer.setPhone("+212587498733");
        mockMvc.perform(MockMvcRequestBuilders.post("/customers").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("firstname").value("Med"))
                .andExpect(MockMvcResultMatchers.jsonPath("lastname").value("Med"))
                .andExpect(MockMvcResultMatchers.jsonPath("email").value("med@gmail.com"));


    }

    @Test
    void getCustomer() {
    }

    @Test
    void listCustomer() {
    }

    @Test
    void updateCustomer() {
    }

    @Test
    void deleteCustomer() {
    }
}