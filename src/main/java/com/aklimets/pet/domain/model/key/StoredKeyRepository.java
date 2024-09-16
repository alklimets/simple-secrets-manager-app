package com.aklimets.pet.domain.model.key;

import com.aklimets.pet.domain.dto.response.key.StoredKeyDTO;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface StoredKeyRepository extends MongoRepository<StoredKey, String> {

    boolean existsByApiKeyAndKeyName(String apiKey, String name);

    List<StoredKeyDTO> findAllByApiKey(String apiKey);
}
