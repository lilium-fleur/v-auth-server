package org.fleur.vauthserver.auth;

import lombok.RequiredArgsConstructor;
import org.fleur.vauthserver.auth.dto.AuthDto;
import org.fleur.vauthserver.auth.dto.RegisterDto;
import org.fleur.vauthserver.exception.BadRequestException;
import org.fleur.vauthserver.user.UserRepository;
import org.fleur.vauthserver.user.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public AuthDto register(RegisterDto registerDto) {
        userRepository.findByUsername(registerDto.username())
                .ifPresent(user -> {
                    throw new BadRequestException("Username is already in use");
                });

        User user = User.builder()
                .email(registerDto.email())
                .username(registerDto.username())
                .password(passwordEncoder.encode(registerDto.password()))
                .build();
        User saved = userRepository.save(user);

        return AuthDto.builder()
                .id(saved.getId())
                .email(saved.getEmail())
                .username(saved.getUsername())
                .build();
    }

}
