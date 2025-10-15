package com.example.bankcards.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Запрос на получение токенов через токен обновления.
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
public class RefreshJwtRequest {

    /**
     * Идентификатор запроса.
     */
    @NotNull(message = "Идентификатор запроса не может быть пустым")
    UUID requestId;

    /**
     * Токен обновления.
     */
    @NotBlank(message = "Токен обновления не может быть пустым")
    public String refreshToken;

}
