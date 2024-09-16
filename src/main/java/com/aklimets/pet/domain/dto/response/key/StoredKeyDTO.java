package com.aklimets.pet.domain.dto.response.key;

import java.util.List;

public record StoredKeyDTO(String id, String keyName, String algorithm, String createdAt, List<StoredKeyVersionDTO> versions) {
}
