package com.aklimets.pet.application.service.apikey;

import com.aklimets.pet.buildingblock.anotations.ApplicationService;
import com.aklimets.pet.domain.dto.request.apikey.ApiKeyActionRequest;
import com.aklimets.pet.domain.dto.request.apikey.ApiKeyGenerationRequest;
import com.aklimets.pet.domain.dto.response.apikey.ApiKeyResponse;
import com.aklimets.pet.domain.dto.response.apikey.ApiKeyRevokeResponse;
import com.aklimets.pet.domain.exception.NotFoundException;
import com.aklimets.pet.domain.model.apikey.ApiKeyFactory;
import com.aklimets.pet.domain.model.apikey.ApiKeyRepository;
import com.aklimets.pet.domain.model.apikey.attribute.ApiKeyStatus;
import com.aklimets.pet.domain.model.apikey.history.ApiKeyHistory;
import com.aklimets.pet.domain.service.ApiKeyDomainService;
import com.aklimets.pet.util.datetime.TimeSource;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@ApplicationService
@AllArgsConstructor
@Slf4j
public class ApiKeyAppService {

    private final ApiKeyRepository apiKeyRepository;

    private final ApiKeyFactory apiKeyFactory;

    private final ApiKeyDomainService apiKeyDomainService;

    private final TimeSource timeSource;

    public ApiKeyResponse generate(ApiKeyGenerationRequest request) {
        var apiKey = apiKeyFactory.create(request);
        apiKeyRepository.save(apiKey);
        return new ApiKeyResponse(apiKey.getApiKey());
    }

    public ApiKeyRevokeResponse revoke(ApiKeyActionRequest request) {
        var apiKey = apiKeyRepository.findByApiKey(request.apiKey())
                .orElseThrow(() -> new NotFoundException("Not found", "Api key not found"));
        apiKey.revoke();
        apiKeyRepository.save(apiKey);
        return new ApiKeyRevokeResponse(ApiKeyStatus.REVOKED);
    }

    public ApiKeyResponse rotate(ApiKeyActionRequest request) {
        var apiKey = apiKeyRepository.findByApiKey(request.apiKey())
                .orElseThrow(() -> new NotFoundException("Not found", "Api key not found"));

        var previousApiKey = apiKey.getApiKey();
        var newApiKey = apiKeyDomainService.generateApiKey();
        var now = timeSource.getCurrentLocalDateTime();

        apiKey.rotateApikey(newApiKey, now);
        apiKey.getHistory().add(0, new ApiKeyHistory(previousApiKey, now));
        apiKeyRepository.save(apiKey);
        return new ApiKeyResponse(newApiKey);
    }
}
