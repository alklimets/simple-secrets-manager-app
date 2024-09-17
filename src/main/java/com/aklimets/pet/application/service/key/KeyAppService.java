package com.aklimets.pet.application.service.key;

import com.aklimets.pet.buildingblock.anotations.ApplicationService;
import com.aklimets.pet.crypto.util.AsymmetricKeyUtil;
import com.aklimets.pet.crypto.util.SymmetricKeyUtil;
import com.aklimets.pet.domain.dto.key.StoredKeyCreationDTO;
import com.aklimets.pet.domain.dto.request.key.KeyGenerationRequest;
import com.aklimets.pet.domain.dto.request.key.PrivateKeyRequest;
import com.aklimets.pet.domain.dto.request.key.PublicKeyRequest;
import com.aklimets.pet.domain.dto.response.disposablekey.DisposableKeyResponse;
import com.aklimets.pet.domain.dto.response.key.*;
import com.aklimets.pet.domain.exception.BadRequestException;
import com.aklimets.pet.domain.exception.NotFoundException;
import com.aklimets.pet.domain.model.disposablekey.DisposableKeyFactory;
import com.aklimets.pet.domain.model.disposablekey.DisposableKeyRepository;
import com.aklimets.pet.domain.model.key.StoredKey;
import com.aklimets.pet.domain.model.key.StoredKeyFactory;
import com.aklimets.pet.domain.model.key.StoredKeyRepository;
import com.aklimets.pet.domain.model.key.keyversion.KeyVersion;
import com.aklimets.pet.util.datetime.TimeSource;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import static com.aklimets.pet.domain.model.disposablekey.attribute.DisposableKeyState.DISPOSED;

@ApplicationService
@AllArgsConstructor
@Slf4j
public class KeyAppService {

    private final AsymmetricKeyUtil asymmetricKeyUtil;

    private final SymmetricKeyUtil symmetricKeyUtil;

    private final StoredKeyFactory storedKeyFactory;

    private final StoredKeyRepository storedKeyRepository;

    private final DisposableKeyRepository disposableKeyRepository;

    private final DisposableKeyFactory disposableKeyFactory;

    private final TimeSource timeSource;

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

    public StoredKeyResponse getKeyByName(String apiKey, String name) {
        return storedKeyRepository.findByApiKeyAndKeyName(apiKey, name)
                .orElseThrow(() -> new NotFoundException("Key not found", "Key with name " + name + " not found"));
    }

    public PublicKeyResponse getPublicKeyByNameAndVersion(PublicKeyRequest request) {
        var key = getStoredKey(request.apiKey(), request.keyName());
        var keyVersion = getKeyVersion(key, request.version());

        return getInitialPublicKeyResponseBuilder(request)
                .id(keyVersion.getVersionId())
                .publicKey(keyVersion.getKeyPair().getPublicKey())
                .keyName(key.getKeyName())
                .algorithm(key.getAlgorithm())
                .state(keyVersion.getState().name())
                .build();
    }

    public PrivateKeyResponse getPrivateKeyByNameAndVersion(String apiKey, String name, String version, PrivateKeyRequest request) throws Exception {
        var key = getStoredKey(apiKey, name);
        var keyVersion = getKeyVersion(key, version);
        var disposableKey = disposableKeyRepository.findById(request.disposableKeyId())
                .orElseThrow(() -> new NotFoundException("Not found", "Disposable key not found"));

        if (disposableKey.getState() == DISPOSED || disposableKey.getValidUntil().isBefore(timeSource.getCurrentLocalDateTime())) {
            throw new BadRequestException("Error", "Disposable key already is disposed or expired");
        }

        var sessionKeyDecrypted = asymmetricKeyUtil.decrypt(request.sessionKey(), disposableKey.getPrivateKey());
        var privateKeyEncrypted = symmetricKeyUtil.encrypt(keyVersion.getKeyPair().getPrivateKey(), sessionKeyDecrypted);

        disposableKey.dispose();
        disposableKeyRepository.save(disposableKey);

        return new PrivateKeyResponse(
                keyVersion.getVersionId(),
                privateKeyEncrypted,
                key.getKeyName(),
                key.getAlgorithm(),
                keyVersion.getState().name());
    }

    @SneakyThrows
    private PublicKeyResponse.PublicKeyResponseBuilder getInitialPublicKeyResponseBuilder(PublicKeyRequest request) {
        if (!request.includeDisposableKey()) return PublicKeyResponse.builder();
        var disposableKey = disposableKeyFactory.create();
        disposableKeyRepository.save(disposableKey);
        var dispose = new DisposableKeyResponse(disposableKey.getId(), disposableKey.getPublicKey(), disposableKey.getValidUntil());
        return PublicKeyResponse.builder().disposableKey(dispose);
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
