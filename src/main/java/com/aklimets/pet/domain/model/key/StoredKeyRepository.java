package com.aklimets.pet.domain.model.key;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface StoredKeyRepository extends MongoRepository<StoredKey, String> {
}
