package com.aklimets.pet.domain.model.key;

import com.aklimets.pet.domain.model.key.keyversion.KeyVersion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "stored_keys")
@AllArgsConstructor
@Getter
public class StoredKey {

    @Id
    private String id;

    private String apiKey;

    private String keyName;

    private LocalDateTime createdAt;

    private String algorithm;

    private List<KeyVersion> versions;

    protected StoredKey() {
    }
}
