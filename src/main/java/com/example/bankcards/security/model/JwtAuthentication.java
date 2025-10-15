package com.example.bankcards.security.model;

import com.example.bankcards.entity.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Set;

/**
 * Модель для хранения данных пользователя после аутентификации.
 */
@Getter
@Setter
public class JwtAuthentication implements Authentication {

    /**
     * Флаг аутентификации.
     */
    private boolean authenticated;

    /**
     * Ник пользователя.
     */
    private String username;

    /**
     * Роли пользователя.
     */
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { return roles; }

    @Override
    public Object getCredentials() { return null; }

    @Override
    public Object getDetails() { return null; }

    @Override
    public Object getPrincipal() { return username; }

    @Override
    public boolean isAuthenticated() { return authenticated; }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    @Nullable
    @Override
    public String getName() { return username; }

}
