package com.example.bankcards.dto.response;

import com.example.bankcards.constant.RoleName;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Ответ для операций с ролями.
 *
 * @param id идентификатор роли
 * @param name имя роли
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record RoleResponse(

        Long id,
        RoleName name
) {
}
