package com.aklimets.pet.domain.dto.request.key;

import jakarta.validation.constraints.NotNull;

public record PrivateKeyRequest(
        @NotNull(message = "Session key cannot be null") String sessionKey,
        @NotNull(message = "Disposable key id cannot be null") String disposableKeyId){
}
