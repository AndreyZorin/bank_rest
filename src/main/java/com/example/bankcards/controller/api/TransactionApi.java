package com.example.bankcards.controller.api;

import com.example.bankcards.dto.request.TransactionRequest;
import com.example.bankcards.dto.response.TransactionResponse;
import com.example.bankcards.util.validation.group.CardBalance;
import com.example.bankcards.util.validation.group.CardTransaction;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Денежные операции", description = "Денежные операции с банковскими картами пользователя")
@RequestMapping("/api/v1/transactions")
@SecurityRequirement(name = "JWT")
public interface TransactionApi {

    /**
     * Денежный перевод меду банковскими картами пользователя.
     *
     * @param request {@link TransactionRequest} запрос на денежный перевод меду банковскими картами пользователя
     * @return {@link ResponseEntity<TransactionResponse>}
     */
    @Operation(summary = "Денежный перевод меду банковскими картами пользователя")
    @TransactionApiResponse
    @PostMapping("/transfer")
    ResponseEntity<TransactionResponse> cardToCardTransfer(
            @Validated(CardTransaction.class)
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Запрос на денежный перевод меду банковскими картами пользователя",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = TransactionRequest.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "Запрос на денежный перевод меду банковскими картами пользователя",
                                                    value = """
                                                                    {
                                                                        "requestId": "123e4567-e89b-12d3-a456-426614174000",
                                                                        "cardNumberSource": "**** **** **** 3241",
                                                                        "cardNumberTarget": "**** **** **** 3241",
                                                                        "sum": 10000.00
                                                                    }
                                                               """
                                            )
                                    }
                            )
                    }
            )
            TransactionRequest request
    );

    /**
     * Баланс карты пользователя.
     *
     * @param request {@link TransactionRequest} запрос баланса карты пользователя
     * @return {@link ResponseEntity<TransactionResponse>}
     */
    @Operation(summary = "Баланс карты пользователя")
    @TransactionApiResponse
    @PostMapping("/balance")
    ResponseEntity<TransactionResponse> cardBalance(
            @Validated(CardBalance.class)
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Запрос баланса карты пользователя",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = TransactionRequest.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "Запрос баланса карты пользователя",
                                                    value = """
                                                                    {
                                                                        "requestId": "123e4567-e89b-12d3-a456-426614174000",
                                                                        "cardNumberTarget": "**** **** **** 3241"
                                                                    }
                                                               """
                                            )
                                    }
                            )
                    }
            )
            TransactionRequest request
    );
}
