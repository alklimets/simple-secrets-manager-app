package com.aklimets.pet.domain.model.apikey;

import com.aklimets.pet.domain.dto.request.ApiKeyGenerationRequest;
import com.aklimets.pet.domain.exception.BadRequestException;
import com.aklimets.pet.domain.model.apikey.attribute.ApiKeyStatus;
import com.aklimets.pet.domain.model.apikey.attribute.ApiKeyType;
import com.aklimets.pet.domain.service.ApiKeyDomainService;
import com.aklimets.pet.util.datetime.TimeSource;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.aklimets.pet.domain.model.apikey.attribute.ApiKeyStatus.ACTIVE;
import static com.aklimets.pet.domain.model.apikey.attribute.ApiKeyType.LIMITED;

@Component
@AllArgsConstructor
@Slf4j
public class ApiKeyFactory {

    private final ApiKeyDomainService apiKeyDomainService;

    private final TimeSource timeSource;

    public ApiKey create(ApiKeyGenerationRequest request) {
        var now = timeSource.getCurrentLocalDateTime();
        return new ApiKey(
                UUID.randomUUID().toString(),
                apiKeyDomainService.generateApiKey(),
                now,
                request.type(),
                calculateExpirationDate(request),
                request.expireAfterDays(),
                ACTIVE
        );
    }

    private LocalDateTime calculateExpirationDate(ApiKeyGenerationRequest request) {
        if (LIMITED == request.type() && request.expireAfterDays() == null) {
            throw new BadRequestException("Bad request", "expireAfterDays is required for LIMITED api key type");
        }
        return LIMITED == request.type() ?
                timeSource.getCurrentLocalDateTime().plusDays(request.expireAfterDays()) :
                null;
    }
}
