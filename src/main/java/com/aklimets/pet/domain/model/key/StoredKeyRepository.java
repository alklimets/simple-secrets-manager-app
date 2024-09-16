package com.aklimets.pet.domain.model.key;

import com.aklimets.pet.domain.dto.response.key.StoredKeyDTO;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface StoredKeyRepository extends MongoRepository<StoredKey, String> {

    boolean existsByApiKeyAndKeyName(String apiKey, String name);

    List<StoredKeyDTO> findAllByApiKey(String apiKey);

    Optional<StoredKeyDTO> findByApiKeyAndKeyName(String apiKey, String name);
}
