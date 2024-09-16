package com.aklimets.pet.domain.model.disposablekey;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface DisposableKeyRepository extends MongoRepository<DisposableKey, String> {

}
