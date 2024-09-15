package com.aklimets.pet.adapter.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/api-keys")
@Tag(name = "Api key API", description = "API to work with api keys generation")
@Slf4j
@AllArgsConstructor
public class ApiKeyController {
}
