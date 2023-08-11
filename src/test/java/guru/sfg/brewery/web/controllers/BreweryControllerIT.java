package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class BreweryControllerIT extends BaseIT{

    @Test
    void findBreweriesNoAuth() throws Exception {
        mockMvc.perform(get("/breweries"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void findBreweriesBasicAuthNotCustomer() throws Exception {
        mockMvc.perform(get("/breweries").with(httpBasic("user", "password")))
                .andExpect(status().isForbidden());
    }

    @Test
    void findBreweriesBasicAuthCustomer() throws Exception {
        mockMvc.perform(get("/breweries").with(httpBasic("scott", "tiger")))
                .andExpect(status().isOk())
                .andExpect(view().name("breweries/index"))
                .andExpect(model().attributeExists("breweries"));
    }
}
