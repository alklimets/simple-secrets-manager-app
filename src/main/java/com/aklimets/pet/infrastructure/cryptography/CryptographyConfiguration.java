package com.aklimets.pet.infrastructure.cryptography;

import com.aklimets.pet.crypto.util.AsymmetricKeyUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CryptographyConfiguration {

    @Bean
    public AsymmetricKeyUtil asymmetricKeyUtil() {
        return new AsymmetricKeyUtil();
    }
}
