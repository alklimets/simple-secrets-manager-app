package com.aklimets.pet.domain.dto.response;

import com.aklimets.pet.domain.model.apikey.attribute.ApiKeyStatus;

public record ApiKeyRevokeResponse(ApiKeyStatus status) {
}
