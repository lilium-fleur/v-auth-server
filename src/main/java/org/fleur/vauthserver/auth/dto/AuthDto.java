package org.fleur.vauthserver.auth.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record AuthDto(
        UUID id,
        String email,
        String username
) {
}
