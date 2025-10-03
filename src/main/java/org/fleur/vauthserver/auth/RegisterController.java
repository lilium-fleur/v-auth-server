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

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class RegisterController {
    private final RegisterService registerService;

    @PostMapping("/register")
    public ResponseEntity<AuthDto> register(
            @RequestBody @Valid final RegisterDto registerDto) {
        return ResponseEntity.ok(registerService.register(registerDto));
    }

}
