package com.aklimets.pet.domain.dto.key;

import com.aklimets.pet.domain.model.key.keyversion.KeyVersion;

import java.util.List;

public record StoredKeyVersionWrapper(List<KeyVersion> versions) {
}
