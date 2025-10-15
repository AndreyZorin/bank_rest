package com.example.bankcards.controller.api;

import com.example.bankcards.dto.response.Response;
import com.example.bankcards.dto.request.UserRequest;
import com.example.bankcards.dto.response.UserResponse;
import com.example.bankcards.util.validation.group.CreateUser;
import com.example.bankcards.util.validation.group.DeleteUser;
import com.example.bankcards.util.validation.group.SearchUser;
import com.example.bankcards.util.validation.group.UpdateUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Управление пользователями", description = "Операции с пользователями")
@RequestMapping("/api/v1/users")
@SecurityRequirement(name = "JWT")
public interface UserApi {

    /**
     * Создание пользователя.
     *
     * @param request {@link UserRequest} запрос на создание пользователя
     * @return {@link ResponseEntity<UserResponse>}
     */
    @Operation(summary = "Создание пользователя")
    @UserApiResponse
    @PostMapping
    ResponseEntity<UserResponse> createUser(
            @Validated(CreateUser.class)
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Запрос на создание пользователя",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserRequest.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "Запрос на создание пользователя",
                                                    value = """
                                                                    {
                                                                        "requestId": "123e4567-e89b-12d3-a456-426614174000",
                                                                        "username": "Nick",
                                                                        "password": "password",
                                                                        "firstName": "Joe",
                                                                        "lastName": "Doe",
                                                                        "roles": [
                                                                              {
                                                                                 "name": "USER"
                                                                              }
                                                                        ]
                                                                    }
                                                               """
                                            )
                                    }
                            )
                    }
            )
            UserRequest request
    );

    /**
     * Изменение данных пользователя.
     *
     * @param request {@link UserRequest} запрос на изменение данных пользователя
     * @return {@link ResponseEntity<UserResponse>}
     */
    @Operation(summary = "Изменение данных пользователя")
    @UserApiResponse
    @PutMapping
    ResponseEntity<UserResponse> updateUser(
            @Validated(UpdateUser.class)
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Запрос на изменение данных пользователя",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserRequest.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "Запрос на изменение данных пользователя",
                                                    value = """
                                                                    {
                                                                        "requestId": "123e4567-e89b-12d3-a456-426614174000",
                                                                        "userId": 1231232134,
                                                                        "username": "NewNick",
                                                                        "roles": [
                                                                              {
                                                                                 "name": "USER"
                                                                              }
                                                                        ]
                                                                    }
                                                               """
                                            )
                                    }
                            )
                    }
            )
            UserRequest request
    );

    /**
     * Удаление данных пользователя.
     *
     * @param request {@link UserRequest} запрос на удаление данных пользователя
     * @return {@link ResponseEntity<Response>}
     */
    @Operation(summary = "Удаление данных пользователя")
    @UserApiResponse
    @DeleteMapping
    ResponseEntity<Response> deleteUser(
            @Validated(DeleteUser.class)
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Запрос на удаление данных пользователя",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserRequest.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "Запрос на удаление данных пользователя",
                                                    value = """
                                                                    {
                                                                        "requestId": "123e4567-e89b-12d3-a456-426614174000",
                                                                        "userId": 1231232134
                                                                    }
                                                               """
                                            )
                                    }
                            )
                    }
            )
            UserRequest request
    );


    /**
     * Поиск пользователей.
     *
     * @param request {@link UserRequest} запрос на поиск пользователей
     * @param pageNumber номер страницы
     * @param pageSize размер страницы
     * @param direction направление: ASC, DESC
     * @param sortField поле сортировки
     * @return {@link ResponseEntity<UserResponse>}
     */
    @Operation(summary = "Поиск пользователей")
    @UserApiResponse
    @PostMapping("/search")
    ResponseEntity<UserResponse> searchUser(
            @Validated(SearchUser.class)
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Поиск пользователей с фильтрацией и пагинацией",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserRequest.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "Запрос на поиск пользователей",
                                                    value = """
                                                                    {
                                                                        "requestId": "123e4567-e89b-12d3-a456-426614174000",
                                                                        "userId": 1231232134,
                                                                        "username": "Nick",
                                                                        "firstName": "Joe",
                                                                        "lastName": "Doe"
                                                                    }
                                                               """
                                            )
                                    }
                            )
                    }
            )
            UserRequest request,
            @RequestParam int pageNumber,
            @RequestParam int pageSize,
            @RequestParam Sort.Direction direction,
            @RequestParam String sortField
    );
}
