package com.aklimets.pet.domain.dto.request;

import com.aklimets.pet.domain.model.apikey.attribute.ApiKeyType;

public record ApiKeyGenerationRequest(ApiKeyType type) {
}
