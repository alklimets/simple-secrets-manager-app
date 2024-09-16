package com.aklimets.pet.domain.model.apikey;

import com.aklimets.pet.domain.model.apikey.attribute.ApiKeyStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface ApiKeyRepository extends MongoRepository<ApiKey, String> {

    Optional<ApiKey> findByApiKey(String apiKey);

    boolean existsByApiKeyAndStatus(String apiKey, ApiKeyStatus status);

}
