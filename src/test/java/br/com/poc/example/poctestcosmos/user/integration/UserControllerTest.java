package br.com.poc.example.poctestcosmos.user.integration;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.poc.example.poctestcosmos.domain.user.User;
import br.com.poc.example.poctestcosmos.infra.IntegrationTestAzureCosmosDb;

@IntegrationTestAzureCosmosDb
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("Test find User by Id")
    @Test
    public void test_findUserById() throws Exception {
        mvc.perform(get("/api/users/Test1"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Test 1")))
                .andExpect(jsonPath("$.id", is("Test1")));
    }

    @DisplayName("Test to persist an User ")
    @Test
    public void test_saveUser() throws Exception {
        String userName = "Test to Persist name";
        User user = new User(null, userName);
        mvc.perform(post("/api/users/")
                .content(usetToJson(user))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.name").value(userName));
    }

    private String usetToJson(User user) {
        try {
            return objectMapper.writeValueAsString(user);
        } catch (Exception ex) {
            return null;
        }
    }

}
