package com.aklimets.pet.domain.dto.key;

public record StoredKeyCreationDTO(String apiKey, String keyName, String algorithm, String publicKey, String privateKey){
}
