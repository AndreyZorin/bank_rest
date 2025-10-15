package com.example.bankcards.controller.api;

import com.example.bankcards.dto.response.ErrorResponse;
import com.example.bankcards.dto.response.JwtResponse;
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
 * Ответы AuthApi.
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
                                       schema = @Schema(implementation = JwtResponse.class),
                                       examples = {
                                               @ExampleObject(
                                                       name = "Ответ с токенами доступа и обновления",
                                                       value = """
                                                                    {
                                                                        "operationStatus": "COMPLETED",
                                                                        "responseId": "123e4567-e89b-12d3-a456-426614174000",
                                                                        "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJTdXBlck1hbjQiLCJleHAiOjE3NjAzMTA1NzcsInJvbGVzIjpbIlVTRVIiXX0.0jcf_wlE5r49nMnlvL5d_HFi0w2f-ssCsPlpcTWwUZTjbfCkzVi8KHmKPoemhoWijCSwnFqMu9RuPST2YC2NxA",
                                                                        "refreshToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJTdXBlck1hbjQiLCJleHAiOjE3NjI5MDIyNzd9.wFx7TLzzErJ9unezD2Znu7JC0z-f_PQWuHmASsfgDGtCTcz98-TD_Ncvw5qeV_lwv3FrasiuBnByqFCArbBAuA",
                                                                        "type": "Bearer"
                                                                    }
                                                               """
                                               ),
                                               @ExampleObject(
                                                       name = "Ответ с токенами доступа",
                                                       value = """
                                                                    {
                                                                        "operationStatus": "COMPLETED",
                                                                        "responseId": "123e4567-e89b-12d3-a456-426614174000",
                                                                        "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJTdXBlck1hbjQiLCJleHAiOjE3NjAzMTA1NzcsInJvbGVzIjpbIlVTRVIiXX0.0jcf_wlE5r49nMnlvL5d_HFi0w2f-ssCsPlpcTWwUZTjbfCkzVi8KHmKPoemhoWijCSwnFqMu9RuPST2YC2NxA",
                                                                        "type": "Bearer"
                                                                    }
                                                               """
                                               ),
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
                                                                        "responseId": "123e4567-e89b-12d3-a456-426614174000",
                                                                        "errorMessage": "Неправильный пароль"
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
public @interface AuthApiResponse {
}
