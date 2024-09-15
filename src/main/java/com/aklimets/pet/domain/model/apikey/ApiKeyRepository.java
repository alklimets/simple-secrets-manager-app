package com.aklimets.pet.domain.model.apikey;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ApiKeyRepository extends MongoRepository<ApiKey, String> {
}
