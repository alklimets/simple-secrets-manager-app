package com.aklimets.pet.adapter.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/keys")
@Tag(name = "Keys API", description = "API to work with keys")
@Slf4j
@AllArgsConstructor
public class KeyController {
}
