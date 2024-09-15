package com.aklimets.pet.application.service.apikey;

import com.aklimets.pet.buildingblock.anotations.ApplicationService;
import com.aklimets.pet.domain.dto.request.ApiKeyGenerationRequest;
import com.aklimets.pet.domain.dto.response.GenerateApiKeyResponse;
import com.aklimets.pet.domain.model.apikey.ApiKeyFactory;
import com.aklimets.pet.domain.model.apikey.ApiKeyRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@ApplicationService
@AllArgsConstructor
@Slf4j
public class ApiKeyAppService {

    private final ApiKeyRepository apiKeyRepository;

    private final ApiKeyFactory apiKeyFactory;

    public GenerateApiKeyResponse generate(ApiKeyGenerationRequest request) {
        var apiKey = apiKeyFactory.create(request.type());
        apiKeyRepository.save(apiKey);
        return new GenerateApiKeyResponse(apiKey.getApiKey());
    }
}
