package com.example.bankcards.security.util;

import com.example.bankcards.constant.RoleName;
import com.example.bankcards.entity.Role;
import com.example.bankcards.security.model.JwtAuthentication;
import io.jsonwebtoken.Claims;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Функции для работы с JWT.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JwtUtils {

    /**
     * Генерация модели для хранения данных пользователя в контексте безопасности.
     *
     * @param claims данные пользователя
     * @return {@link JwtAuthentication}
     */
    @NonNull
    public static JwtAuthentication generate(@NonNull Claims claims) {
        final JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setRoles(getRoles(claims));
        jwtInfoToken.setUsername(claims.getSubject());
        return jwtInfoToken;
    }

    @NonNull
    private static Set<Role> getRoles(@NonNull Claims claims) {
        final List<String> roles = claims.get("roles", List.class);
        return roles.stream()
                .map(role -> new Role(RoleName.valueOf(role)))
                .collect(Collectors.toSet());
    }

}