package com.example.bankcards.service;

import com.example.bankcards.dto.response.Response;
import com.example.bankcards.dto.request.UserRequest;
import com.example.bankcards.dto.response.UserResponse;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;

/**
 * Сервис для работы с пользователем.
 */
public interface UserService {

    /**
     * Создание пользователя.
     *
     * @param request запрос на создание пользователя
     * @return {@link UserResponse}
     */
    @NonNull
    UserResponse createUser(@NonNull UserRequest request);

    /**
     * Обновление пользователя.
     *
     * @param request запрос на обновление пользователя
     * @return {@link UserResponse}
     */
    @NonNull
    UserResponse updateUser(@NonNull UserRequest request);

    /**
     * Удаление пользователя.
     *
     * @param request запрос на удаление пользователя
     * @return {@link Response}
     */
    @NonNull
    Response deleteUser(@NonNull UserRequest request);

    /**
     * Поиск пользователя с фильтрацией, сортировкой и пагинацией.
     *
     * @param request запрос на поиск пользователя
     * @param pageNumber номер страницы
     * @param pageSize размер страницы
     * @param direction направление (ASC, DESC)
     * @param sortField поле сортировки
     * @return {@link UserResponse}
     */
    @NonNull
    UserResponse searchUser(
            @NonNull UserRequest request,
            int pageNumber,
            int pageSize,
            @NonNull Sort.Direction direction,
            @NonNull String sortField
    );
}
