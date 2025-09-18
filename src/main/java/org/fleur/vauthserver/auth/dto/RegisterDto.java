package org.fleur.vauthserver.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

public record RegisterDto(
        @Email(message = "Please enter valid email address")
        String email,
        @NotBlank
        @Size(min = 3, max = 50)
        String username,
        @Size(min = 6, max = 200)
        @NotBlank
        String password
) {
}
