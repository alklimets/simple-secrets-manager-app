package com.aklimets.pet.domain.dto.response.key;

public record PrivateKeyResponse(String id, String privateKey, String keyName, String algorithm, String state) {
}
