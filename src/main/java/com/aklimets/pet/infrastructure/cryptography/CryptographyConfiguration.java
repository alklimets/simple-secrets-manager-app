package com.aklimets.pet.infrastructure.cryptography;

import com.aklimets.pet.crypto.util.AsymmetricKeyUtil;
import com.aklimets.pet.crypto.util.SymmetricKeyUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CryptographyConfiguration {

    @Bean
    public AsymmetricKeyUtil asymmetricKeyUtil() {
        return new AsymmetricKeyUtil();
    }

    @Bean
    public SymmetricKeyUtil symmetricKeyUtil() {
        return new SymmetricKeyUtil();
    }
}
