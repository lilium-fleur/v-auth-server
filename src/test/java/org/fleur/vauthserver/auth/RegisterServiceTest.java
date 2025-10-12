package org.fleur.vauthserver.auth;

import org.fleur.vauthserver.auth.dto.AuthDto;
import org.fleur.vauthserver.auth.dto.RegisterDto;
import org.fleur.vauthserver.exception.BadRequestException;
import org.fleur.vauthserver.user.UserRepository;
import org.fleur.vauthserver.user.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegisterServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private RegisterService registerService;

    @Test
    void registerNewUserTest() {
        UUID userId = UUID.randomUUID();
        String email = "user@mail.com";
        String rawPassword = "password";
        String encodedPassword = "encoded";
        String username = "user";
        RegisterDto registerDto = new RegisterDto(email, username, rawPassword);
        AuthDto authDto = new AuthDto(userId, email, username);

        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);
        when(userRepository.existsByEmail(email)).thenReturn(false);
        when(userRepository.existsByUsername(username)).thenReturn(false);
        when(userRepository.save(argThat(u -> u.getEmail().equals(email)
                && u.getUsername().equals(username)
                && u.getPassword().equals(encodedPassword))))
                .thenAnswer(i -> {
                    User user = i.getArgument(0);
                    return User.builder()
                            .id(userId)
                            .email(user.getEmail())
                            .username(user.getUsername())
                            .password(user.getPassword())
                            .build();
                });

        AuthDto result = registerService.register(registerDto);

        assertEquals(result, authDto);
        verify(passwordEncoder, times(1)).encode(rawPassword);
    }

    @Test
    void registerWithExistingEmailTest() {
        String email = "user@mail.com";
        String rawPassword = "password";
        String username = "user";
        RegisterDto registerDto = new RegisterDto(email, username, rawPassword);

        when(userRepository.existsByEmail(email)).thenReturn(true);

        assertThrows(BadRequestException.class, () -> registerService.register(registerDto));
    }

    @Test
    void registerWithExistingUsernameTest() {
        String email = "user@mail.com";
        String rawPassword = "password";
        String username = "user";
        RegisterDto registerDto = new RegisterDto(email, username, rawPassword);

        when(userRepository.existsByUsername(username)).thenReturn(true);

        assertThrows(BadRequestException.class, () -> registerService.register(registerDto));
    }
}