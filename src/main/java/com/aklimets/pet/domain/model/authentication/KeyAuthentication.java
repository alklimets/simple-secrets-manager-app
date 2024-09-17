package com.aklimets.pet.domain.model.authentication;

import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Collections;

@Getter
public class KeyAuthentication implements Authentication {

    private final String apiKey;

    private boolean authenticated;

    public KeyAuthentication(String apiKey) {
        this.apiKey = apiKey;
        this.authenticated = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.EMPTY_LIST;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return apiKey;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean authenticated) throws IllegalArgumentException {
        Assert.isTrue(!authenticated, "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        this.authenticated = authenticated;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String toString() {
        return "KeyAuthentication{" +
                "apiKey='" + apiKey + '\'' +
                '}';
    }
}
