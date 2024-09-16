package com.aklimets.pet.infrastructure.security.constants;

public class SecurityConstants {

    public static String[] WHITE_LIST_URLS = {
            "/api/v1/api-keys/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/webjars/**",
            "/openapi",
            "/openapi/**"
    };
}
