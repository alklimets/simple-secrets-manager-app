package com.aklimets.pet.application.service.key;

import com.aklimets.pet.buildingblock.anotations.ApplicationService;
import com.aklimets.pet.crypto.util.AsymmetricKeyUtil;
import com.aklimets.pet.domain.dto.key.KeyDTO;
import com.aklimets.pet.domain.dto.request.key.KeyGenerationRequest;
import com.aklimets.pet.domain.dto.response.key.GeneratedKeyResponse;
import com.aklimets.pet.domain.model.key.StoredKeyFactory;
import com.aklimets.pet.domain.model.key.StoredKeyRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@ApplicationService
@AllArgsConstructor
@Slf4j
public class KeyAppService {

    private final AsymmetricKeyUtil asymmetricKeyUtil;

    private final StoredKeyFactory storedKeyFactory;

    private final StoredKeyRepository storedKeyRepository;

    public GeneratedKeyResponse generate(KeyGenerationRequest request, String apiKey) throws Exception {
        var keyPair = asymmetricKeyUtil.generateEncodedKeyPair(request.algorithm());
        var keyDTO = new KeyDTO(
                apiKey,
                request.keyName(),
                request.algorithm().getAlgorithm(),
                keyPair.publicKey(),
                keyPair.privateKey()
        );
        var storedKey = storedKeyFactory.create(keyDTO);
        storedKeyRepository.save(storedKey);
        return new GeneratedKeyResponse(storedKey.getId(),
                storedKey.getKeyName(),
                storedKey.getAlgorithm(),
                storedKey.getCreatedAt().toString());
    }
}
