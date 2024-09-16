package com.aklimets.pet.adapter.controller;

import com.aklimets.pet.application.service.disposablekey.DisposableKeyAppService;
import com.aklimets.pet.domain.dto.response.disposablekey.DisposableKeyResponse;
import com.aklimets.pet.swagger.annotation.DefaultSwaggerEndpoint;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/disposable-keys")
@Tag(name = "Disposable keys API", description = "API to work with disposable keys")
@Slf4j
@AllArgsConstructor
public class DisposableKeyController {

    private final DisposableKeyAppService disposableKeyAppService;

    @DefaultSwaggerEndpoint
    @Operation(summary = "Generate disposable RSA key pair")
    @PostMapping("/generate")
    public DisposableKeyResponse generate() throws Exception {
        return disposableKeyAppService.generate();
    }

}
