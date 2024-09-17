package com.aklimets.pet.domain.dto.response.key;

import java.util.List;

public record StoredKeyResponse(String id, String keyName, String algorithm, String createdAt, List<StoredKeyVersionResponse> versions) {
}
