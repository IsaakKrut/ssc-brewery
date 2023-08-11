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
        mockMvc.perform(get("/brewery/breweries"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void findBreweriesBasicAuthUser() throws Exception {
        mockMvc.perform(get("/brewery/breweries").with(httpBasic("user", "password")))
                .andExpect(status().isForbidden());
    }

    @Test
    void findBreweriesBasicAuthAdmin() throws Exception {
        mockMvc.perform(get("/brewery/breweries").with(httpBasic("spring", "guru")))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("breweries/index"))
                .andExpect(model().attributeExists("breweries"));
    }

    @Test
    void findBreweriesBasicAuthCustomer() throws Exception {
        mockMvc.perform(get("/brewery/breweries").with(httpBasic("scott", "tiger")))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("breweries/index"))
                .andExpect(model().attributeExists("breweries"));
    }
}
