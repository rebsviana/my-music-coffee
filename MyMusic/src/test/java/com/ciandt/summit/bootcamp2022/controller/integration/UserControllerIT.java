package com.ciandt.summit.bootcamp2022.controller.integration;

import com.ciandt.summit.bootcamp2022.config.Factory;
import com.ciandt.summit.bootcamp2022.service.impl.TokenAuthorizerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import javax.transaction.Transactional;
import static com.ciandt.summit.bootcamp2022.config.Factory.USER_NICKNAME;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserControllerIT {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TokenAuthorizerService tokenAuthorizerService;

    @Test
    @DisplayName("When save new user then return UserDto")
    void whenSaveNewUserThenReturnUserDto() throws Exception {


        String jsonBody = objectMapper.writeValueAsString(Factory.createUserDtoNonExistent());

        when(tokenAuthorizerService.verifyTokenAuthorizer(anyString())).thenReturn(ResponseEntity.ok("Ok"));

        ResultActions result =
                mockMvc.perform(post("/api/user")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
    }

    @Test
    @DisplayName("When get user by nickname then return ResponseEntity with List of UserDto")
    void whenGetUserByNicknameThenReturnResponseEntityWithListUserDto() throws Exception {

        ResultActions result =
                mockMvc.perform(get("/api/user/{USER_NICKNAME}", USER_NICKNAME));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.data").exists());
        result.andExpect(jsonPath("$.data[0].name").value("mariana"));
        result.andExpect(jsonPath("$.data[0].nickname").value("mariana"));
        result.andExpect(jsonPath("$.data[0].userType").value("COMMON"));
    }
}
