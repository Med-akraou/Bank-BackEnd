package med.sig.bank.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import med.sig.bank.dtos.CustomerDTO;
import med.sig.bank.exceptions.NotFoundCustomerException;
import med.sig.bank.servises.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


@WebMvcTest(CustomerController.class)
class CustomerControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    CustomerService customerService;

    private CustomerDTO customerDTO;

    private ObjectMapper mapper = new ObjectMapper();

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
        BDDMockito.given(customerService.saveCustomer(ArgumentMatchers.any())).willReturn(customerDTO);
        mockMvc.perform(MockMvcRequestBuilders.post("/customers").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(customerDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("firstname").value("Med"))
                .andExpect(MockMvcResultMatchers.jsonPath("lastname").value("Med"))
                .andExpect(MockMvcResultMatchers.jsonPath("email").value("med@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("phone").value("+212587498733"));
    }

    @Test
    void shouldReturnCustomer() throws Exception {
        //given
        String customerId = "customerId";
        BDDMockito.given(customerService.getCustomer(customerId)).willReturn(customerDTO);

        //when then
        mockMvc.perform(MockMvcRequestBuilders.get("/customers/{customerId}",customerId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("firstname").value("Med"))
                .andExpect(MockMvcResultMatchers.jsonPath("lastname").value("Med"))
                .andExpect(MockMvcResultMatchers.jsonPath("email").value("med@gmail.com"));

    }

    @Test
    void shouldThrowNotFoundCustomer() throws Exception {
        //given
        BDDMockito.given(customerService.getCustomer("fake-customer-id")).willThrow(NotFoundCustomerException.class);

        //when, then
        mockMvc.perform(MockMvcRequestBuilders.get("/customers/{customerId}","fake-customer-id"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void shouldReturnListOfCustomers() throws Exception {
        //given
        List<CustomerDTO> customersDto = new ArrayList<>();
        Stream.of("Hassan Chafi","Iman Hossni","Farid Majidi").forEach(name->{
            CustomerDTO customerDto = new CustomerDTO();
            customerDto.setFirstname(name.split(" ")[0]);
            customerDto.setLastname(name.split(" ")[1]);
            customerDto.setEmail(name.replace(" ","")+"@gmail.com");
            customerDto.setPhone("+212347850933");
            customersDto.add(customerDto);
        });
        BDDMockito.given(customerService.litCustomers()).willReturn(customersDto);
        //when then
        mockMvc.perform(MockMvcRequestBuilders.get("/customers"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(mapper.writeValueAsString(customersDto)));
        Mockito.verify(customerService).litCustomers();
    }

    @Test
    void shouldUpdateCustomer() throws Exception {

        //given
        String customerId = "customerId";
        BDDMockito.given(customerService.updateCostumer(customerId,customerDTO)).willReturn(customerDTO);

        //when then
        mockMvc.perform(MockMvcRequestBuilders.put("/customers/{customerId}",customerId).contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(customerDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("firstname").value("Med"))
                .andExpect(MockMvcResultMatchers.jsonPath("lastname").value("Med"))
                .andExpect(MockMvcResultMatchers.jsonPath("email").value("med@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("phone").value("+212587498733"));
        Mockito.verify(customerService).updateCostumer(customerId,customerDTO);

    }
    @Test
    void when_update_shouldThrowNotFoundCustomer() throws Exception {
        BDDMockito.given(customerService.updateCostumer("fake-customer-id",customerDTO)).willThrow(NotFoundCustomerException.class);
        mockMvc.perform(MockMvcRequestBuilders.put("/customers/{customerId}","fake-customer-id").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(customerDTO)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void shouldDeleteCustomer() throws Exception {
        //given
        String customerId = "customerId";
        Mockito.doNothing().when(customerService).deleteCustomer(customerDTO.getCustomerId());

        //when then
        mockMvc.perform(MockMvcRequestBuilders.delete("/customers/{customerId}",customerId))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(customerService).deleteCustomer(customerId);
    }

    @Test
    void when_delete_shouldThrowNotFoundCustomer() throws Exception {
        //when
        Mockito.doThrow(NotFoundCustomerException.class).when(customerService).deleteCustomer("fake-customer-id");

        //when then
        mockMvc.perform(MockMvcRequestBuilders.delete("/customers/{customerId}","fake-customer-id"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}