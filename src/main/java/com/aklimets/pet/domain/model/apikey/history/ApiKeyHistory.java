package com.aklimets.pet.domain.model.apikey.history;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@AllArgsConstructor
@Getter
public class ApiKeyHistory {

    private String previousApiKey;

    private LocalDateTime rotatedAt;

    protected ApiKeyHistory() {
    }

}
