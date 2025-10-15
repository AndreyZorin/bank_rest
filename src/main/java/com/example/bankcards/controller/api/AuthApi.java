package com.example.bankcards.controller.api;

import com.example.bankcards.dto.request.JwtRequest;
import com.example.bankcards.dto.response.JwtResponse;
import com.example.bankcards.dto.request.RefreshJwtRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.security.auth.message.AuthException;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * API управления токенами доступа.
 */
@Tag(name = "Управление токенами доступа", description = "Операции с токенами доступа")
@RequestMapping("/api/v1/auth")
public interface AuthApi {

    /**
     * Получение токена доступа и токена обновления.
     *
     * @param request {@link JwtRequest} запрос на получение токена доступа и токена обновления по логину и паролю
     * @return {@link ResponseEntity<JwtResponse>}
     * @throws AuthException {@link AuthException} ошибка аутентификации
     */
    @Operation(summary = "Получение токена доступа и токена обновления")
    @AuthApiResponse
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<JwtResponse> login(
            @Valid
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Запрос на получение токена доступа и токена обновления по логину и паролю",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = JwtRequest.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "Запрос на получение токена доступа и токена обновления по логину и паролю",
                                                    value = """
                                                                    {
                                                                        "requestId": "123e4567-e89b-12d3-a456-426614174000",
                                                                        "login": "SuperMan",
                                                                        "password": "**********"
                                                                    }
                                                               """
                                            )
                                    }
                            )
                    }
            )
            JwtRequest request
    ) throws AuthException;

    /**
     * Получение токена доступа.
     *
     * @param request {@link RefreshJwtRequest} запрос на получение токена доступа по токену обновления
     * @return {@link ResponseEntity<JwtResponse>}
     */
    @Operation(summary = "Получение токена доступа")
    @AuthApiResponse
    @PostMapping("/token")
    ResponseEntity<JwtResponse> getNewAccessToken(
            @Valid
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Запрос на получение токена доступа по токену обновления",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = RefreshJwtRequest.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "Запрос на получение токена доступа по токену обновления",
                                                    value = """
                                                                    {
                                                                        "requestId": "123e4567-e89b-12d3-a456-426614174000",
                                                                        "refreshToken": "SuperMan"
                                                                    }
                                                               """
                                            )
                                    }
                            )
                    }
            )
            RefreshJwtRequest request
    );

    /**
     * Получение токена доступа и нового токена обновления.
     *
     * @param request {@link RefreshJwtRequest} запрос на получение токена доступа и нового токена обновления по токену обновления
     * @return {@link ResponseEntity<JwtResponse>}
     * @throws AuthException {@link AuthException} ошибка аутентификации
     */
    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Получение токена доступа и нового токена обновления")
    @AuthApiResponse
    @PostMapping("/refresh")
    ResponseEntity<JwtResponse> getNewRefreshToken(
            @Valid
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Запрос на получение токена доступа и нового токена обновления по токену обновления",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = RefreshJwtRequest.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "Запрос на получение токена доступа и нового токена обновления по токену обновления",
                                                    value = """
                                                                    {
                                                                        "requestId": "123e4567-e89b-12d3-a456-426614174000",
                                                                        "refreshToken": "SuperMan"
                                                                    }
                                                               """
                                            )
                                    }
                            )
                    }
            )
            RefreshJwtRequest request
    ) throws AuthException;
}
