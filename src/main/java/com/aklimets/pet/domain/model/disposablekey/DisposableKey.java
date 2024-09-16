package com.aklimets.pet.domain.model.disposablekey;

import com.aklimets.pet.domain.model.disposablekey.attribute.DisposableKeyState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "disposable_keys")
@AllArgsConstructor
@Getter
public class DisposableKey {

    @Id
    private String id;

    private String publicKey;

    private String privateKey;

    private DisposableKeyState state;

    private LocalDateTime validUntil;

    protected DisposableKey() {
    }

    public void dispose() {
        this.state = DisposableKeyState.DISPOSED;
    }

}
