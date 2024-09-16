package com.aklimets.pet.application.service.key;

import com.aklimets.pet.buildingblock.anotations.ApplicationService;
import com.aklimets.pet.crypto.util.AsymmetricKeyUtil;
import com.aklimets.pet.domain.dto.key.StoredKeyCreationDTO;
import com.aklimets.pet.domain.dto.request.key.KeyGenerationRequest;
import com.aklimets.pet.domain.dto.response.key.GeneratedKeyResponse;
import com.aklimets.pet.domain.dto.response.key.StoredKeyDTO;
import com.aklimets.pet.domain.dto.response.key.StoredKeysResponse;
import com.aklimets.pet.domain.exception.BadRequestException;
import com.aklimets.pet.domain.exception.NotFoundException;
import com.aklimets.pet.domain.model.key.StoredKeyFactory;
import com.aklimets.pet.domain.model.key.StoredKeyRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@ApplicationService
@AllArgsConstructor
@Slf4j
public class KeyAppService {

    private final AsymmetricKeyUtil asymmetricKeyUtil;

    private final StoredKeyFactory storedKeyFactory;

    private final StoredKeyRepository storedKeyRepository;

    public GeneratedKeyResponse generate(KeyGenerationRequest request, String apiKey) throws Exception {
        if (storedKeyRepository.existsByApiKeyAndKeyName(apiKey, request.keyName())) {
            throw new BadRequestException("Error exists", "Key with name " + request.keyName() + " already exists");
        }
        var keyPair = asymmetricKeyUtil.generateEncodedKeyPair(request.algorithm());
        var keyDTO = new StoredKeyCreationDTO(
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

    public StoredKeysResponse getKeys(String apiKey) {
        var apiKeys = storedKeyRepository.findAllByApiKey(apiKey);
        return new StoredKeysResponse(apiKeys);

    }

    public StoredKeyDTO getKeyByName(String apiKey, String name) {
        return storedKeyRepository.findByApiKeyAndKeyName(apiKey, name)
                .orElseThrow(() -> new NotFoundException("Key not found", "Key with name " + name + " not found"));
    }
}
