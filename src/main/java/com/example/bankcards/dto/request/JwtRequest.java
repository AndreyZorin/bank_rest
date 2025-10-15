package com.example.bankcards.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Запрос на аутентификацию.
 */
@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
public class JwtRequest {

    /**
     * Идентификатор запроса.
     */
    @NotNull(message = "Идентификатор запроса не может быть пустым")
    UUID requestId;

    /**
     * Логин(ник) пользователя
     */
    @NotBlank(message = "Логин пользователя не может быть пустым")
    private String login;

    /**
     * Пароль пользователя
     */
    @NotBlank(message = "Парль пользователя не может быть пустым")
    private String password;

    @Override
    public String toString() {
        return "JwtRequest{" +
                "requestId=" + requestId +
                ", login='" + login + '\'' +
                ", password='*****'" +
                '}';
    }
}
