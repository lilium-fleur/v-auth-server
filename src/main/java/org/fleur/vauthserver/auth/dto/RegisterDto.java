package org.fleur.vauthserver.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record RegisterDto(
        @Email(message = "Please enter valid email address.")
        String email,
        @Size(min = 3, max = 50, message = "Username size must be more than 3 characters.")
        String username,
        @Size(min = 6, max = 200, message = "Password size must be more than 6 characters.")
        String password
) {
}
