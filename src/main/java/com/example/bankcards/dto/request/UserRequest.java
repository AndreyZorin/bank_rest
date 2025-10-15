package com.example.bankcards.dto.request;

import com.example.bankcards.util.validation.group.CreateUser;
import com.example.bankcards.util.validation.group.DeleteUser;
import com.example.bankcards.util.validation.group.SearchUser;
import com.example.bankcards.util.validation.group.UpdateUser;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.springframework.lang.NonNull;

import java.util.Set;
import java.util.UUID;

/**
 * Запрос на операции с пользователями.
 *
 * @param requestId идентификатор запроса
 * @param userId идентификатор пользователя
 * @param username ник пользователя
 * @param password пароль пользователя
 * @param firstName имя пользователя
 * @param lastName фамилия пользователя
 * @param roles роли пользователя
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record UserRequest(

        @Getter
        @NotNull(message = "Идентификатор запроса не может быть пустым",
        groups = {CreateUser.class, UpdateUser.class, DeleteUser.class, SearchUser.class})
        UUID requestId,

        @NotNull(message = "Идентификатор пользователя не может быть пустым",
        groups = {UpdateUser.class, DeleteUser.class})
        Long userId,

        @NotBlank(message = "Ник пользователя не может быть пустым",
                groups = {CreateUser.class})
        @Size(max = 20, message = "В нике должно быть не больше 20 символов",
                groups = {CreateUser.class, UpdateUser.class, SearchUser.class})
        String username,

        @NotBlank(message = "Пароль пользователя не может быть пустым",
                groups = {CreateUser.class})
        @Size(max = 20, message = "В пароле должно быть не больше 20 символов",
                groups = {CreateUser.class})
        String password,

        @NotBlank(message = "Имя пользователя не может быть пустым",
                groups = {CreateUser.class})
        String firstName,

        @NotBlank(message = "Фамилия пользователя не может быть пустой",
                groups = {CreateUser.class})
        String lastName,

        @NotEmpty(message = "Список ролей пользователя не может быть пустой",
                groups = {CreateUser.class})
        @Valid
        Set<RoleRequest> roles
) {

    @NonNull
    @Override
    public String toString() {
        return "UserRequest{" +
                "requestId=" + requestId +
                ", userId=" + userId +
                ", username='" + username + '\'' +
                ", password='*****'" +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", roles=" + roles +
                '}';
    }
}
