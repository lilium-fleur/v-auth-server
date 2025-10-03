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
public class RegisterService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public AuthDto register(RegisterDto registerDto) {
        if (userRepository.existsByEmail(registerDto.email())) {
            throw new BadRequestException("Email taken");
        }
        if (userRepository.existsByUsername(registerDto.username())) {
            throw new BadRequestException("Username taken");
        }

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
