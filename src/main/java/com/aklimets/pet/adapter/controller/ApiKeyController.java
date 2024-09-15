package com.aklimets.pet.adapter.controller;

import com.aklimets.pet.application.service.apikey.ApiKeyAppService;
import com.aklimets.pet.domain.dto.request.ApiKeyGenerationRequest;
import com.aklimets.pet.domain.dto.response.GenerateApiKeyResponse;
import com.aklimets.pet.swagger.annotation.DefaultSwaggerEndpoint;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/api-keys")
@Tag(name = "Api key API", description = "API to work with api keys generation")
@Slf4j
@AllArgsConstructor
public class ApiKeyController {

    private final ApiKeyAppService apiKeyAppService;

    @DefaultSwaggerEndpoint
    @Operation(summary = "Generate api key")
    @PostMapping("/generate")
    public GenerateApiKeyResponse generate(@NotNull @Valid @RequestBody ApiKeyGenerationRequest request) {
        return apiKeyAppService.generate(request);
    }
}
