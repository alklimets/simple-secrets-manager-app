package com.aklimets.pet.domain.dto.request.key;

import com.aklimets.pet.crypto.model.AsymmetricAlgorithm;
import jakarta.validation.constraints.NotNull;

public record KeyGenerationRequest(
        @NotNull(message = "Algorithm cannot be null") AsymmetricAlgorithm algorithm,
        @NotNull(message = "Key name cannot be null") String keyName) {
}
