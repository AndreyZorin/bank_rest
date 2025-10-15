package com.example.bankcards.dto.filter;

/**
 * Фильтр для расширенного поиска пользователей.
 *
 * @param userId идентификатор пользователя
 * @param username ник пользователя
 * @param firstName имя пользователя
 * @param lastName фамилия пользователя
 */
public record UserSearchFilter(

        Long userId,
        String username,
        String firstName,
        String lastName
) {
}
