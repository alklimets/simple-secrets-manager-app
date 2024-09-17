package com.aklimets.pet.domain.model.key;

import com.aklimets.pet.domain.dto.response.key.StoredKeyResponse;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StoredKeyRepository extends MongoRepository<StoredKey, String> {

    boolean existsByApiKeyAndKeyName(String apiKey, String name);

    List<StoredKeyResponse> findAllByApiKey(String apiKey);

    Optional<StoredKeyResponse> findByApiKeyAndKeyName(String apiKey, String name);

    @Query(value = "{ 'apiKey' : ?0, 'keyName' : ?1 }")
    Optional<StoredKey> getByApiKeyAndKeyName(String apiKey, String name);
}
