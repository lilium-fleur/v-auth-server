package org.fleur.vauthserver.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.fleur.vauthserver.auth.dto.AuthDto;
import org.fleur.vauthserver.auth.dto.RegisterDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthDto> register(
            @ModelAttribute @Valid final RegisterDto authDto) {
        return ResponseEntity.ok(authService.register(authDto));
    }

}
