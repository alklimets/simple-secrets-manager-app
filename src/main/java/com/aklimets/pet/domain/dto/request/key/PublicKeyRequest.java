package com.aklimets.pet.domain.dto.request.key;

public record PublicKeyRequest(String apiKey, String keyName, String version, boolean includeDisposableKey) {
}
