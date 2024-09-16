package com.aklimets.pet.application.service.key;

import com.aklimets.pet.buildingblock.anotations.ApplicationService;
import com.aklimets.pet.crypto.util.AsymmetricKeyUtil;
import com.aklimets.pet.crypto.util.SymmetricKeyUtil;
import com.aklimets.pet.domain.dto.key.StoredKeyCreationDTO;
import com.aklimets.pet.domain.dto.request.key.KeyGenerationRequest;
import com.aklimets.pet.domain.dto.request.key.PrivateKeyRequestDTO;
import com.aklimets.pet.domain.dto.response.key.*;
import com.aklimets.pet.domain.exception.BadRequestException;
import com.aklimets.pet.domain.exception.NotFoundException;
import com.aklimets.pet.domain.model.disposablekey.DisposableKeyRepository;
import com.aklimets.pet.domain.model.key.StoredKey;
import com.aklimets.pet.domain.model.key.StoredKeyFactory;
import com.aklimets.pet.domain.model.key.StoredKeyRepository;
import com.aklimets.pet.domain.model.key.keyversion.KeyVersion;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@ApplicationService
@AllArgsConstructor
@Slf4j
public class KeyAppService {

    private final AsymmetricKeyUtil asymmetricKeyUtil;

    private final SymmetricKeyUtil symmetricKeyUtil;

    private final StoredKeyFactory storedKeyFactory;

    private final StoredKeyRepository storedKeyRepository;

    private final DisposableKeyRepository disposableKeyRepository;

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

    public PublicKeyDTO getPublicKeyByNameAndVersion(String apiKey, String name, String version) {
        var key = getStoredKey(apiKey, name);
        var keyVersion = getKeyVersion(key, version);

        return new PublicKeyDTO(
                keyVersion.getVersionId(),
                keyVersion.getKeyPair().getPublicKey(),
                key.getKeyName(),
                key.getAlgorithm(),
                keyVersion.getState().name());
    }

    public PrivateKeyDTO getPrivateKeyByNameAndVersion(String apiKey, String name, String version, PrivateKeyRequestDTO request) throws Exception {
        var key = getStoredKey(apiKey, name);
        var keyVersion = getKeyVersion(key, version);
        var disposableKey = disposableKeyRepository.findById(request.disposableKeyId())
                .orElseThrow(() -> new NotFoundException("Not found", "Disposable key not found"));

        var sessionKeyDecrypted = asymmetricKeyUtil.decrypt(request.sessionKey(), disposableKey.getPrivateKey());
        var privateKeyEncrypted = symmetricKeyUtil.encrypt(keyVersion.getKeyPair().getPrivateKey(), sessionKeyDecrypted);

        disposableKey.dispose();
        disposableKeyRepository.save(disposableKey);

        return new PrivateKeyDTO(
                keyVersion.getVersionId(),
                privateKeyEncrypted,
                key.getKeyName(),
                key.getAlgorithm(),
                keyVersion.getState().name());
    }

    private StoredKey getStoredKey(String apiKey, String name) {
        return storedKeyRepository.getByApiKeyAndKeyName(apiKey, name)
                .orElseThrow(() -> new NotFoundException("Key not found", "Key with name " + name + " not found"));
    }

    private KeyVersion getKeyVersion(StoredKey key, String version) {
        return key.getVersions().stream()
                .filter(kv -> kv.getVersionId().equals(version))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Key not found", "Public key with version " + version + " not found"));
    }
}
