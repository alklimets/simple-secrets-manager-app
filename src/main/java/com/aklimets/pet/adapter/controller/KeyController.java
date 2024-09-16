package com.aklimets.pet.adapter.controller;

import com.aklimets.pet.application.service.key.KeyAppService;
import com.aklimets.pet.domain.dto.request.key.KeyGenerationRequest;
import com.aklimets.pet.domain.dto.request.key.PrivateKeyRequestDTO;
import com.aklimets.pet.domain.dto.response.key.*;
import com.aklimets.pet.swagger.annotation.DefaultSwaggerEndpoint;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/keys")
@Tag(name = "Keys API", description = "API to work with keys")
@Slf4j
@AllArgsConstructor
public class KeyController {

    private final KeyAppService keyAppService;

    @DefaultSwaggerEndpoint
    @Operation(summary = "Get keys info by ApiKey")
    @GetMapping
    public StoredKeysResponse getKeys(@AuthenticationPrincipal String apiKey) {
        return keyAppService.getKeys(apiKey);
    }

    @DefaultSwaggerEndpoint
    @Operation(summary = "Get keys info by ApiKey")
    @GetMapping("/{name}")
    public StoredKeyDTO getKeyByName(@AuthenticationPrincipal String apiKey, @PathVariable String name) {
        return keyAppService.getKeyByName(apiKey, name);
    }

    @DefaultSwaggerEndpoint
    @Operation(summary = "Get public key by key name and version for ApiKey")
    @GetMapping("/{name}/public/{version}")
    public PublicKeyDTO getPublicKeyByNameAndVersion(@AuthenticationPrincipal String apiKey,
                                                     @PathVariable String name,
                                                     @PathVariable String version) {
        return keyAppService.getPublicKeyByNameAndVersion(apiKey, name, version);
    }

    @DefaultSwaggerEndpoint
    @Operation(summary = "Get public key by key name and version for ApiKey")
    @PostMapping("/{name}/private/{version}")
    public PrivateKeyDTO getPrivateKeyByNameAndVersion(@AuthenticationPrincipal String apiKey,
                                                       @PathVariable String name,
                                                       @PathVariable String version,
                                                       @RequestBody PrivateKeyRequestDTO request) throws Exception {
        return keyAppService.getPrivateKeyByNameAndVersion(apiKey, name, version, request);
    }

    @DefaultSwaggerEndpoint
    @Operation(summary = "Generate key pair")
    @PostMapping("/generate")
    public GeneratedKeyResponse generate(@Valid @RequestBody KeyGenerationRequest request,
                                         @AuthenticationPrincipal String apiKey) throws Exception { // value from principal field in authentication object
        return keyAppService.generate(request, apiKey);
    }
}
