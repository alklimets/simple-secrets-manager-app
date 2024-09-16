package com.aklimets.pet.domain.model.key;

import com.aklimets.pet.domain.dto.key.StoredKeyCreationDTO;
import com.aklimets.pet.domain.model.key.keyversion.KeyVersion;
import com.aklimets.pet.domain.model.key.keyversion.attribute.KeyVersionState;
import com.aklimets.pet.domain.model.key.keyversion.keypair.StoredKeyPair;
import com.aklimets.pet.util.datetime.TimeSource;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@AllArgsConstructor
@Slf4j
public class StoredKeyFactory {

    private final TimeSource timeSource;

    public StoredKey create(StoredKeyCreationDTO keyDTO) {
        var keyPair = new StoredKeyPair(keyDTO.publicKey(), keyDTO.privateKey());
        var keyVersion = new KeyVersion(
                UUID.randomUUID().toString(),
                KeyVersionState.CURRENT,
                timeSource.getCurrentLocalDateTime(),
                keyPair
        );

        return new StoredKey(
                UUID.randomUUID().toString(),
                keyDTO.apiKey(),
                keyDTO.keyName(),
                timeSource.getCurrentLocalDateTime(),
                keyDTO.algorithm(),
                List.of(keyVersion)
        );
    }
}
