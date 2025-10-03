package org.fleur.vauthserver.exception;

import lombok.Builder;

@Builder
public record ErrorDto(
        int statusCode,
        String message,
        String description
) {
}
