package com.aklimets.pet.domain.dto.request;

import jakarta.validation.constraints.NotNull;

public record ApiKeyActionRequest(@NotNull(message = "Api key cannot be null") String apiKey) {
}
