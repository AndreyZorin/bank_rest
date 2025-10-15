package com.example.bankcards.security.service;

import com.example.bankcards.dto.request.JwtRequest;
import com.example.bankcards.dto.request.RefreshJwtRequest;
import com.example.bankcards.dto.response.JwtResponse;
import com.example.bankcards.security.model.JwtAuthentication;
import jakarta.security.auth.message.AuthException;
import org.springframework.lang.NonNull;

/**
 * Сервис аутентификации.
 */
public interface AuthService {

    /**
     * Получение токена доступа и токена обновления.
     *
     * @param authRequest {@link JwtRequest} запрос на получение токена доступа и токена обновления по логину и паролю
     * @return {@link JwtResponse}
     * @throws AuthException {@link AuthException} ошибка аутентификации
     */
    @NonNull
    JwtResponse login(@NonNull JwtRequest authRequest) throws AuthException;

    /**
     * Получение токена доступа.
     *
     * @param request {@link RefreshJwtRequest} запрос с токеном обновления
     * @return {@link JwtResponse}
     */
    @NonNull
    JwtResponse getAccessToken(@NonNull RefreshJwtRequest request);

    /**
     * Получение токена доступа и обновления.
     *
     * @param request {@link String} запрос с токеном обновления
     * @return {@link JwtResponse}
     * @throws AuthException ошибка аутентификации
     */
    @NonNull
    JwtResponse refresh(@NonNull RefreshJwtRequest request) throws AuthException;

    /**
     * Получение информации о пользователе и его правах доступа из контекста безопасности.
     *
     * @return {@link JwtAuthentication}
     */
    @NonNull
    JwtAuthentication getAuthInfo();
}
