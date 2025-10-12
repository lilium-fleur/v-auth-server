package org.fleur.vauthserver.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.fleur.vauthserver.auth.dto.AuthDto;
import org.fleur.vauthserver.auth.dto.RegisterDto;
import org.fleur.vauthserver.user.CustomUserDetailsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = RegisterController.class)
@AutoConfigureMockMvc(addFilters = false)
class RegisterControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private RegisterService registerService;
    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void registerOk() throws Exception {
        RegisterDto registerDto = new RegisterDto("email@email.com", "username", "password123");
        String json = objectMapper.writeValueAsString(registerDto);

        when(registerService.register(any(RegisterDto.class))).thenAnswer(invocation -> {
            RegisterDto dto = invocation.getArgument(0);
            return AuthDto.builder()
                    .id(UUID.randomUUID())
                    .email(dto.email())
                    .username(dto.username())
                    .build();
        });

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                        .with(csrf())
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.email").value("email@email.com"))
                .andExpect(jsonPath("$.username").value("username"));
    }

    @Test
    void registerError() throws Exception {
        RegisterDto registerDto = new RegisterDto("ss", "dd", "ff");
        String json = objectMapper.writeValueAsString(registerDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                        .with(csrf())
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
}