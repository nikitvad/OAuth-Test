package com.nikitvad.profitskill.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.JVM)
@ActiveProfiles("test")
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    private TokenDto tokenDto;

    private MockHttpServletRequestBuilder loginParam;

    @Before
    public void init() {
        String clientAuthHeader = "Basic bXktdHJ1c3RlZC1jbGllbnQ6c2VjcmV0";
        loginParam = MockMvcRequestBuilders
                .post("/oauth/token")
                .header("Authorization", clientAuthHeader)
                .param("username", "registrationtest")
                .param("password", "password")
                .param("grant_type", "password");
    }

    @Test
    public void registerUser() throws Exception {

        UserRegistrationDto userDto = UserRegistrationDto.builder()
                .username("registrationtest")
                .password("password")
                .build();

        String s = objectMapper.writeValueAsString(userDto);

        mvc.perform(MockMvcRequestBuilders.post("/user/register").contentType("application/json").content(s))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void loginTest() throws Exception {
        MvcResult mvcResult = mvc.perform(loginParam).andExpect(MockMvcResultMatchers
                .status().isOk()).andReturn();

        tokenDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), TokenDto.class);

        assertNotNull(tokenDto);
        assertNotNull(tokenDto.getAccessToken());
        assertNotNull(tokenDto.getRefreshToken());
        assertTrue(tokenDto.getExpiresIn() > 0);
    }

    @Test
    public void refreshTokenTest() throws Exception {

        MvcResult mvcResult = mvc.perform(loginParam).andExpect(MockMvcResultMatchers
                .status().isOk()).andReturn();

        tokenDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), TokenDto.class);


        String clientAuthHeader = "Basic bXktdHJ1c3RlZC1jbGllbnQ6c2VjcmV0";
        loginParam = MockMvcRequestBuilders
                .post("/oauth/token")
                .header("Authorization", clientAuthHeader)
                .param("refresh_token", tokenDto.getRefreshToken())
                .param("grant_type", "refresh_token");

        mvc.perform(loginParam).andExpect(MockMvcResultMatchers.status().isOk());

        tokenDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), TokenDto.class);

        assertNotNull(tokenDto);
        assertNotNull(tokenDto.getAccessToken());
        assertNotNull(tokenDto.getRefreshToken());
        assertTrue(tokenDto.getExpiresIn() > 0);
    }

    @Test
    public void meTest() throws Exception {

        MvcResult mvcResult = mvc.perform(loginParam).andExpect(MockMvcResultMatchers
                .status().isOk()).andReturn();

        tokenDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), TokenDto.class);

        String accessTokenHeader = tokenDto.getTokenType() + " " + tokenDto.getAccessToken();
        String authorization = mvc.perform(MockMvcRequestBuilders.get("/user/me")
                .header("Authorization", accessTokenHeader))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertTrue(!authorization.contains("password"));
        assertTrue(authorization.contains("username"));

    }

    @Test
    public void getAllUsers() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/user")).andExpect(MockMvcResultMatchers.status().isUnauthorized());

        MvcResult mvcResult = mvc.perform(loginParam).andExpect(MockMvcResultMatchers
                .status().isOk()).andReturn();

        tokenDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), TokenDto.class);

        String accessTokenHeader = tokenDto.getTokenType() + " " + tokenDto.getAccessToken();
        mvc.perform(MockMvcRequestBuilders.get("/user")
                .header("Authorization", accessTokenHeader))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }
}