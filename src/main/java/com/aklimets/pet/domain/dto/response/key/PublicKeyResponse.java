package com.aklimets.pet.domain.dto.response.key;

import com.aklimets.pet.domain.dto.response.disposablekey.DisposableKeyResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PublicKeyResponse {

    private String id;
    private String publicKey;
    private String keyName;
    private String algorithm;
    private String state;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private DisposableKeyResponse disposableKey;

}
