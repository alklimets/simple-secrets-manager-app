package com.aklimets.pet.domain.model.key.keyversion.keypair;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@AllArgsConstructor
@Getter
public class StoredKeyPair {

    private String publicKey;

    private String privateKey;

    protected StoredKeyPair() {
    }
}
