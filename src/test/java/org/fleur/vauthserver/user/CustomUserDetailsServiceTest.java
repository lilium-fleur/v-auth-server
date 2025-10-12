package org.fleur.vauthserver.user;

import org.fleur.vauthserver.user.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void loadUserByExistUsername() {
        String username = "test";
        String password = "test";
        User repoUser = User.builder().username(username).password(password).build();
        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(username)
                .password(password)
                .build();

        when(userRepository.findByUsername(username)).thenReturn(Optional.ofNullable(repoUser));

        UserDetails loadedUser = customUserDetailsService.loadUserByUsername(username);

        assertEquals(userDetails, loadedUser);
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void loadUserByNonExistUsername() {
        String username = "test";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> customUserDetailsService.loadUserByUsername(username));
        verify(userRepository, times(1)).findByUsername(username);
    }
}