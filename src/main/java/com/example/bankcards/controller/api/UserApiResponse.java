package com.example.bankcards.controller.api;

import com.example.bankcards.dto.response.ErrorResponse;
import com.example.bankcards.dto.response.UserResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Ответы UserApi.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ApiResponses(
        value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Успешная обработка запроса",
                        content = {
                               @Content(
                                       mediaType = MediaType.APPLICATION_JSON_VALUE,
                                       schema = @Schema(implementation = UserResponse.class),
                                       examples = {
                                               @ExampleObject(
                                                       name = "Ответ с данными пользователя",
                                                       value = """
                                                                    {
                                                                        "operationStatus": "COMPLETED",
                                                                        "responseId": "123e4567-e89b-12d3-a456-426614174000",
                                                                        "userInfoList": [
                                                                            {
                                                                                "userId": 123233241,
                                                                                "username": "Nick",
                                                                                "firstName": "Joe",
                                                                                "lastName": "Doe",
                                                                                "roles": [
                                                                                    {
                                                                                       "name": "USER"
                                                                                    }
                                                                                 ]
                                                                            }
                                                                        ]
                                                                    }
                                                               """
                                               )
                                       }
                               )
                        }
                ),
                @ApiResponse(
                        responseCode = "400",
                        description = "Запрос не соответствует заявленной схеме данных и не может быть обработан",
                        content = {
                               @Content(
                                       mediaType = MediaType.APPLICATION_JSON_VALUE,
                                       schema = @Schema(implementation = ErrorResponse.class),
                                       examples = {
                                               @ExampleObject(
                                                       name = "Ответ с сообщением об ошибке валидации",
                                                       value = """
                                                                    {
                                                                        "operationStatus": "ERROR",
                                                                        "responseId": "123e4567-e89b-12d3-a456-426614174000",
                                                                        "errorMessage": "Нарушение формата данных"
                                                                    }
                                                               """
                                               )
                                       }
                               )
                        }
                ),
                @ApiResponse(
                        responseCode = "403",
                        description = "Обработка закончилась с ошибкой авторизации",
                        content = {
                                @Content(
                                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                                        schema = @Schema(implementation = ErrorResponse.class),
                                        examples = {
                                                @ExampleObject(
                                                        name = "Ответ с сообщением об ошибке авторизации",
                                                        value = """
                                                                    {
                                                                        "operationStatus": "ERROR",
                                                                        "responseId": "91a0a5a8-9428-46b2-961e-d14a8485b48b",
                                                                        "errorMessage": "Ошибка авторизации: Access Denied"
                                                                    }
                                                               """
                                                )
                                        }
                                )
                        }
                ),
                @ApiResponse(
                        responseCode = "404",
                        description = "Обработка закончилась с ошибкой поиска",
                        content = {
                               @Content(
                                       mediaType = MediaType.APPLICATION_JSON_VALUE,
                                       schema = @Schema(implementation = ErrorResponse.class),
                                       examples = {
                                               @ExampleObject(
                                                       name = "Ответ с сообщением об ошибке поиска",
                                                       value = """
                                                                    {
                                                                        "operationStatus": "ERROR",
                                                                        "responseId": "123e4567-e89b-12d3-a456-426614174000",
                                                                        "errorMessage": "Пользователь не найден"
                                                                    }
                                                               """
                                               )
                                       }
                               )
                        }
                ),
                @ApiResponse(
                        responseCode = "500",
                        description = "Обработка закончилась внутренней ошибкой сервиса",
                        content = {
                               @Content(
                                       mediaType = MediaType.APPLICATION_JSON_VALUE,
                                       schema = @Schema(implementation = ErrorResponse.class),
                                       examples = {
                                               @ExampleObject(
                                                       name = "Ответ с сообщением о внутренней ошибкой сервиса",
                                                       value = """
                                                                    {
                                                                        "operationStatus": "ERROR",
                                                                        "responseId": "123e4567-e89b-12d3-a456-426614174000",
                                                                        "errorMessage": "Внутренняя ошибка сервиса"
                                                                    }
                                                               """
                                               )
                                       }
                               )
                        }
                )
        }
)
public @interface UserApiResponse {
}
