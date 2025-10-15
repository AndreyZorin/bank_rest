package com.example.bankcards.security.service;

import com.example.bankcards.entity.User;
import io.jsonwebtoken.*;
import org.springframework.lang.NonNull;

/**
 * Сервис генерации и валидации токенов доступа и обновления.
 */
public interface JwtProvider {

    /**
     * Генерация токена доступа.
     *
     * @param user пользователь
     * @return токен доступа
     */
    @NonNull
    String generateAccessToken(@NonNull User user);

    /**
     * Генерация токена обновления.
     *
     * @param user пользователь
     * @return токен обновления
     */
    @NonNull
    String generateRefreshToken(@NonNull User user);

    /**
     * Валидация токена доступа.
     *
     * @param accessToken токен доступа
     * @return результат валидации
     */
    boolean validateAccessToken(@NonNull String accessToken);

    /**
     * Валидация токена обновления.
     *
     * @param refreshToken токен обновления
     * @return результат валидации
     */
    boolean validateRefreshToken(@NonNull String refreshToken);

    /**
     * Получение данных о пользователе из токена доступа.
     *
     * @param token токен доступа
     * @return данные о пользователе
     */
    @NonNull
    Claims getAccessClaims(@NonNull String token);

    /**
     * Получение данных о пользователе из токена обновления.
     *
     * @param token токен обновления
     * @return данные о пользователе
     */
    @NonNull
    Claims getRefreshClaims(@NonNull String token);
}
