package com.aklimets.pet.domain.model.disposablekey;

import com.aklimets.pet.crypto.model.AsymmetricAlgorithm;
import com.aklimets.pet.crypto.util.AsymmetricKeyUtil;
import com.aklimets.pet.domain.model.disposablekey.attribute.DisposableKeyState;
import com.aklimets.pet.util.datetime.TimeSource;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@AllArgsConstructor
@Slf4j
public class DisposableKeyFactory {

    private final AsymmetricKeyUtil asymmetricKeyUtil;

    private final TimeSource timeSource;

    public DisposableKey create() throws Exception {
        var keyPair = asymmetricKeyUtil.generateEncodedKeyPair(AsymmetricAlgorithm.RSA);
        return new DisposableKey(
                UUID.randomUUID().toString(),
                keyPair.publicKey(),
                keyPair.privateKey(),
                DisposableKeyState.ACTIVE,
                timeSource.getCurrentLocalDateTime().plusHours(1)
        );
    }
}
