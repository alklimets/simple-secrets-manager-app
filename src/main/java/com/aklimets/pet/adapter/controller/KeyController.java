package com.aklimets.pet.adapter.controller;

import com.aklimets.pet.application.service.key.KeyAppService;
import com.aklimets.pet.domain.dto.request.key.KeyGenerationRequest;
import com.aklimets.pet.domain.dto.response.key.GeneratedKeyResponse;
import com.aklimets.pet.swagger.annotation.DefaultSwaggerEndpoint;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/keys")
@Tag(name = "Keys API", description = "API to work with keys")
@Slf4j
@AllArgsConstructor
public class KeyController {

    private final KeyAppService keyAppService;

    @DefaultSwaggerEndpoint
    @Operation(summary = "Generate RSA key pair")
    @PostMapping("/generate")
    public GeneratedKeyResponse generate(KeyGenerationRequest request) throws Exception {
        return keyAppService.generate(request, "fake-api-key");
    }
}
