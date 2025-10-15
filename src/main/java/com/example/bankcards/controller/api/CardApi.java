package com.example.bankcards.controller.api;

import com.example.bankcards.dto.request.CardRequest;
import com.example.bankcards.dto.response.CardResponse;
import com.example.bankcards.dto.response.Response;
import com.example.bankcards.util.validation.group.*;
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

/**
 * Управление банковскими картами.
 */
@Tag(name = "Управление банковскими картами", description = "Операции с банковскими картами пользователя")
@RequestMapping("/api/v1/cards")
@SecurityRequirement(name = "JWT")
public interface CardApi {

    /**
     * Создание карты.
     *
     * @param request {@link CardRequest} запрос на создание карты
     * @return {@link ResponseEntity<CardResponse>}
     */
    @Operation(summary = "Создание карты")
    @CardApiResponse
    @PostMapping
    ResponseEntity<CardResponse> createCard(
            @Validated(CreateCard.class)
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Запрос на создание карты",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CardRequest.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "Запрос на создание карты",
                                                    value = """
                                                                    {
                                                                        "requestId": "123e4567-e89b-12d3-a456-426614174000",
                                                                        "cardNumber": "**** **** **** 3241",
                                                                        "userId": 12323213,
                                                                        "validityPeriod": "2026-10-10",
                                                                        "status": "ACTIVE",
                                                                        "balance": 10000.00
                                                                    }
                                                               """
                                            )
                                    }
                            )
                    }
            )
            CardRequest request
    );

    /**
     * Изменение данных карты.
     *
     * @param request {@link CardRequest} запрос на изменение данных карты
     * @return {@link ResponseEntity<CardResponse>}
     */
    @Operation(summary = "Изменение данных карты")
    @CardApiResponse
    @PatchMapping
    ResponseEntity<CardResponse> updateCard(
            @Validated(UpdateCard.class)
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Запрос на изменение данных карты",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CardRequest.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "Запрос на изменение данных карты",
                                                    value = """
                                                                    {
                                                                        "requestId": "123e4567-e89b-12d3-a456-426614174000",
                                                                        "cardNumber": "**** **** **** 3241",
                                                                        "status": "ACTIVE"
                                                                    }
                                                               """
                                            )
                                    }
                            )
                    }
            )
            CardRequest request
    );

    /**
     * Удаление карты.
     *
     * @param request {@link CardRequest} запрос на удаление карты
     * @return {@link ResponseEntity<Response>}
     */
    @Operation(summary = "Удаление карты")
    @CardApiResponse
    @DeleteMapping
    ResponseEntity<Response> deleteCard(
            @Validated(DeleteCard.class)
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Запрос на удаление карты",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CardRequest.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "Запрос на удаление карты",
                                                    value = """
                                                                    {
                                                                        "requestId": "123e4567-e89b-12d3-a456-426614174000",
                                                                        "cardNumber": "**** **** **** 3241"
                                                                    }
                                                               """
                                            )
                                    }
                            )
                    }
            )
            CardRequest request
    );

    /**
     * Поиск банковских карт пользователей.
     *
     * @param request {@link CardRequest} запрос на поиск банковских карт
     * @param pageNumber номер страницы
     * @param pageSize размер страницы
     * @param direction направление: ASC, DESC
     * @param sortField поле сортировки
     * @return {@link ResponseEntity<CardResponse>}
     */
    @Operation(summary = "Поиск банковских карт пользователей")
    @CardApiResponse
    @PostMapping("/search")
    ResponseEntity<CardResponse> searchCard(
            @Validated(SearchCard.class)
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Поиск банковских карт с фильтрацией и пагинацией",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CardRequest.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "Запрос на поиск банковских карт",
                                                    value = """
                                                                    {
                                                                        "requestId": "123e4567-e89b-12d3-a456-426614174000",
                                                                        "cardId": 1232312,
                                                                        "cardNumber": "**** **** **** 3241",
                                                                        "ownerUsername": "SuperMan",
                                                                        "ownerFullName": "Joe Doe",
                                                                        "status": "ACTIVE"
                                                                    }
                                                               """
                                            )
                                    }
                            )
                    }
            )
            CardRequest request,
            @RequestParam int pageNumber,
            @RequestParam int pageSize,
            @RequestParam Sort.Direction direction,
            @RequestParam String sortField
    );

    /**
     * Запрос пользователя на блокировку карты.
     *
     * @param request {@link CardRequest} запрос пользователя на блокировку карты
     * @return {@link ResponseEntity<Response>}
     */
    @Operation(summary = "Запрос пользователя на блокировку карты")
    @CardApiResponse
    @PostMapping("/block-request")
    ResponseEntity<Response> blockRequestCard(
            @Validated(BlockRequestCard.class)
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Запрос пользователя на блокировку карты",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CardRequest.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "Запрос пользователя на блокировку карты",
                                                    value = """
                                                                    {
                                                                        "requestId": "123e4567-e89b-12d3-a456-426614174000",
                                                                        "cardNumber": "**** **** **** 3241"
                                                                    }
                                                               """
                                            )
                                    }
                            )
                    }
            )
            CardRequest request
    );
}
