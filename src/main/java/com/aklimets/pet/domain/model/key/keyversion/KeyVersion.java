package com.aklimets.pet.domain.model.key.keyversion;

import com.aklimets.pet.domain.model.key.keyversion.attribute.KeyVersionState;
import com.aklimets.pet.domain.model.key.keyversion.keypair.StoredKeyPair;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@AllArgsConstructor
@Getter
public class KeyVersion {

    @Id
    private String versionId;

    private KeyVersionState state;

    private LocalDateTime createdAt;

    private StoredKeyPair keyPair;

    protected KeyVersion() {
    }

}
