package com.aklimets.pet.domain.model.apikey;

import com.aklimets.pet.domain.model.apikey.attribute.ApiKeyStatus;
import com.aklimets.pet.domain.model.apikey.attribute.ApiKeyType;
import com.aklimets.pet.domain.model.apikey.history.ApiKeyHistory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.*;

@Document(collection = "api_keys")
@Getter
@AllArgsConstructor
public class ApiKey {

    @Id
    private String id;

    private String apiKey;

    private LocalDateTime createdAt;

    private ApiKeyType type;

    private LocalDateTime expiresAt;

    private Integer expiresAfterDays;

    private ApiKeyStatus status;

    private List<ApiKeyHistory> history = new LinkedList<>();

    protected ApiKey() {
    }

    public void rotateApikey(String apiKey, LocalDateTime now) {
        this.apiKey = apiKey;
        if (type == ApiKeyType.LIMITED) {
            expiresAt = now.plusDays(expiresAfterDays);
        }
    }

    public void revoke() {
        status = ApiKeyStatus.REVOKED;
    }

}
