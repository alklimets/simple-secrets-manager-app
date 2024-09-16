package com.aklimets.pet.domain.dto.response.disposablekey;

import java.time.LocalDateTime;

public record DisposableKeyResponse(String keyId, String publicKey, LocalDateTime validUntil) {
}
