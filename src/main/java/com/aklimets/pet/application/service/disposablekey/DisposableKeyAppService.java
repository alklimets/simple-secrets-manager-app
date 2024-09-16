package com.aklimets.pet.application.service.disposablekey;

import com.aklimets.pet.buildingblock.anotations.ApplicationService;
import com.aklimets.pet.domain.dto.response.disposablekey.DisposableKeyResponse;
import com.aklimets.pet.domain.model.disposablekey.DisposableKeyFactory;
import com.aklimets.pet.domain.model.disposablekey.DisposableKeyRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@ApplicationService
@AllArgsConstructor
@Slf4j
public class DisposableKeyAppService {

    private final DisposableKeyRepository disposableKeyRepository;

    private final DisposableKeyFactory disposableKeyFactory;

    public DisposableKeyResponse generate() throws Exception {
        var disposableKey = disposableKeyFactory.create();
        disposableKeyRepository.save(disposableKey);
        return new DisposableKeyResponse(disposableKey.getId(), disposableKey.getPublicKey(), disposableKey.getValidUntil());
    }
}
