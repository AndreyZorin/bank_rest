package com.example.bankcards.mapper;

import com.example.bankcards.dto.filter.UserSearchFilter;
import com.example.bankcards.dto.request.UserRequest;
import com.example.bankcards.dto.response.UserResponse;
import com.example.bankcards.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * Мапер для работы с сущностью пользователя и её моделями.
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * Преобразование из запроса {@link UserRequest} в сущность {@link User}.
     *
     * @param request запрос {@link UserRequest}
     * @return сущность {@link User}
     */
    @Mapping(target = "id" , ignore = true)
    @Mapping(target = "password" , ignore = true)
    @Mapping(target = "roles" , ignore = true)
    @Mapping(target = "createdAt" , ignore = true)
    @Mapping(target = "updatedAt" , ignore = true)
    @Mapping(target = "authorities" , ignore = true)
    User toUser(@Nullable UserRequest request);

    /**
     * Преобразование из сущности {@link User} в модель данных {@link UserResponse.UserInfo}.
     *
     * @param user сущность {@link User}
     * @return модель данных {@link UserResponse.UserInfo}
     */
    @Mapping(target = "userId", source = "id")
    UserResponse.UserInfo toUserInfo(@Nullable User user);

    /**
     * Преобразование из списка сущностей {@link User} в список моделей данных {@link UserResponse.UserInfo}.
     *
     * @param users список сущностей {@link User}
     * @return список моделей данных {@link UserResponse.UserInfo}
     */
    List<UserResponse.UserInfo> toUserInfoList(@Nullable List<User> users);

    /**
     * Преобразование из запроса {@link UserRequest} в фильтр для поиска {@link UserSearchFilter}.
     *
     * @param request запрос {@link UserRequest}
     * @return фильтр для поиска {@link UserSearchFilter}
     */
    UserSearchFilter toUserSearchFilter(@Nullable UserRequest request);
}
