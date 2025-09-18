package org.fleur.vauthserver.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.fleur.vauthserver.auth.dto.AuthDto;
import org.fleur.vauthserver.auth.dto.RegisterDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthDto> register(
            @RequestBody @Valid final RegisterDto authDto) {
        return ResponseEntity.ok(authService.register(authDto));
    }
}
