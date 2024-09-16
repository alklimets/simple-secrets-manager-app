package com.aklimets.pet.domain.model.apikey;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ApiKeyRepository extends MongoRepository<ApiKey, String> {

    Optional<ApiKey> findByApiKey(String apiKey);

    boolean existsByApiKey(String apiKey);

}
