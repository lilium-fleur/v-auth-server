package org.fleur.vauthserver.exception;

import lombok.Builder;

import java.util.List;

@Builder
public record ErrorDto(
        int statusCode,
        List<String> message,
        String description
) {
}
