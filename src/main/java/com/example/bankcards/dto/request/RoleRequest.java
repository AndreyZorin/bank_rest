package com.example.bankcards.dto.request;

import com.example.bankcards.constant.RoleName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;

/**
 * Запрос на операции с ролью пользователя.
 *
 * @param name имя роли
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record RoleRequest(

        @NotNull(message = "Имя роли пользователя не может быть пустым")
        RoleName name
) {
}
