package org.fleur.vauthserver.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record LoginDto(
        @Email(message = "Please enter valid email address")
        String email,
        @Size(min = 6, max = 200)
        String password
) {
}
