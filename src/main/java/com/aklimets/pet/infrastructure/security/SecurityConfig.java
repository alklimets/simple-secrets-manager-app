package com.aklimets.pet.infrastructure.security;

import com.aklimets.pet.domain.model.apikey.ApiKeyRepository;
import com.aklimets.pet.infrastructure.security.constants.SecurityConstants;
import com.aklimets.pet.infrastructure.security.filter.ApiKeyAuthenticationFilter;
import com.aklimets.pet.infrastructure.security.filter.RequestIdFilter;
import com.aklimets.pet.infrastructure.security.handler.KeySuccessHandler;
import com.aklimets.pet.infrastructure.security.provider.KeyAuthenticationProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@Slf4j
@AllArgsConstructor
public class SecurityConfig {

    private final AuthenticationEntryPoint entryPoint;

    private final ApiKeyRepository  apiKeyRepository;

    private KeyAuthenticationProvider keyAuthenticationProvider;

    private final RequestIdFilter requestIdFilter;

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(Collections.singletonList(keyAuthenticationProvider));
    }

    private ApiKeyAuthenticationFilter apiKeyAuthenticationFilter() {
        return new ApiKeyAuthenticationFilter(apiKeyRepository, authenticationManager(), new KeySuccessHandler());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(AbstractHttpConfigurer::disable);
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                        .requestMatchers(SecurityConstants.WHITE_LIST_URLS).permitAll()
                        .anyRequest().authenticated()
        );
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.exceptionHandling(handler -> handler.authenticationEntryPoint(entryPoint));

        http.addFilterBefore(apiKeyAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(requestIdFilter, ApiKeyAuthenticationFilter.class);
        return http.build();
    }

}
