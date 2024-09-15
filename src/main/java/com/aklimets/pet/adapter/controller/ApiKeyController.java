package com.aklimets.pet.adapter.controller;

import com.aklimets.pet.application.service.apikey.ApiKeyAppService;
import com.aklimets.pet.domain.dto.request.ApiKeyActionRequest;
import com.aklimets.pet.domain.dto.request.ApiKeyGenerationRequest;
import com.aklimets.pet.domain.dto.response.ApiKeyResponse;
import com.aklimets.pet.domain.dto.response.ApiKeyRevokeResponse;
import com.aklimets.pet.swagger.annotation.DefaultSwaggerEndpoint;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
    public ApiKeyResponse generate(@Valid @RequestBody ApiKeyGenerationRequest request) {
        return apiKeyAppService.generate(request);
    }

    @DefaultSwaggerEndpoint
    @Operation(summary = "Revoke api key")
    @PutMapping("/revoke")
    public ApiKeyRevokeResponse revoke(@Valid @RequestBody ApiKeyActionRequest request) {
        return apiKeyAppService.revoke(request);
    }

    @DefaultSwaggerEndpoint
    @Operation(summary = "Rotate api key")
    @PutMapping("/rotate")
    public ApiKeyResponse rotate(@Valid @RequestBody ApiKeyActionRequest request) {
        return apiKeyAppService.rotate(request);
    }
}
