package com.aklimets.pet.domain.model.apikey;

import com.aklimets.pet.domain.model.apikey.attribute.ApiKeyStatus;
import com.aklimets.pet.domain.model.apikey.attribute.ApiKeyType;
import com.aklimets.pet.domain.service.ApiKeyDomainService;
import com.aklimets.pet.util.datetime.TimeSource;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@AllArgsConstructor
@Slf4j
public class ApiKeyFactory {

    private final ApiKeyDomainService apiKeyDomainService;

    private final TimeSource timeSource;

    public ApiKey create(ApiKeyType type) {
        var now = timeSource.getCurrentLocalDateTime();
        return new ApiKey(
                UUID.randomUUID().toString(),
                apiKeyDomainService.generateApiKey(),
                now,
                type,
                now.plusYears(1),
                ApiKeyStatus.ACTIVE
        );
    }
}
