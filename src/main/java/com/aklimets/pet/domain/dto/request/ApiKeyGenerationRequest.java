package com.aklimets.pet.domain.dto.request;

import com.aklimets.pet.domain.model.apikey.attribute.ApiKeyType;
import jakarta.validation.constraints.NotNull;

public record ApiKeyGenerationRequest(
        @NotNull(message = "Type cannot be null") ApiKeyType type,
        Integer expireAfterDays) {
}
