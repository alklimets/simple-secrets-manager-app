package com.aklimets.pet.infrastructure.security.filter;

import com.aklimets.pet.domain.dto.authentication.KeyAuthentication;
import com.aklimets.pet.domain.model.apikey.ApiKeyRepository;
import com.aklimets.pet.infrastructure.security.handler.KeySuccessHandler;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import java.io.IOException;

@Slf4j
public class ApiKeyAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final ApiKeyRepository apiKeyRepository;

    public ApiKeyAuthenticationFilter(ApiKeyRepository apiKeyRepository,
                                      AuthenticationManager authenticationManager,
                                      KeySuccessHandler keySuccessHandler) {
        super("/api/v1/keys/**");
        this.apiKeyRepository = apiKeyRepository;
        setAuthenticationManager(authenticationManager);
        setAuthenticationSuccessHandler(keySuccessHandler);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
        var apiKey = extractApiKeyFromHeader(request);

        Authentication authentication = null;
        if (apiKeyRepository.existsByApiKey(apiKey)) {
            authentication = new KeyAuthentication(apiKey);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            log.warn("API key is not found");
            sendUnauthorizedError(response);
        }
        return authentication;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
        chain.doFilter(request, response);
    }

    private String extractApiKeyFromHeader(HttpServletRequest request) {
        return request.getHeader("APP-API-KEY");
    }

    private void sendUnauthorizedError(HttpServletResponse response) throws IOException {
        response.sendError(401, "UNAUTHORIZED");
    }
}
