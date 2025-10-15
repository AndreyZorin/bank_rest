package com.example.bankcards.dto.request;

import com.example.bankcards.util.validation.group.CardBalance;
import com.example.bankcards.util.validation.group.CardTransaction;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import lombok.Getter;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.util.UUID;

import static com.example.bankcards.util.MaskingUtils.maskCardNumber;

/**
 * Запрос на транзакционные операции со счетами карт.
 *
 * @param requestId идентификатор запроса
 * @param cardNumberSource номер карты для списания
 * @param cardNumberTarget номер карты для зачисления
 * @param sum сумма транзакции
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record TransactionRequest(

        @Getter
        @NotNull(message = "Идентификатор запроса не может быть пустым",
        groups = {CardTransaction.class, CardBalance.class})
        UUID requestId,

        @NotBlank(message = "Номер карты не может быть пустым",
                groups = {CardTransaction.class})
        @Pattern(
                regexp = "\\d{4}\\s\\d{4}\\s\\d{4}\\s\\d{4}",
                message = "Номер карты не соответствует паттерну",
                groups = {CardTransaction.class}
        )
        String cardNumberSource,

        @NotBlank(message = "Номер карты не может быть пустым",
                groups = {CardTransaction.class, CardBalance.class})
        @Pattern(
                regexp = "\\d{4}\\s\\d{4}\\s\\d{4}\\s\\d{4}",
                message = "Номер карты не соответствует паттерну",
                groups = {CardTransaction.class, CardBalance.class}
        )
        String cardNumberTarget,

        @Digits(integer = 15, fraction = 2)
        @NotNull(message = "Сумма перевода не может быть пустой",
                groups = {CardTransaction.class})
        BigDecimal sum
) {

    @NonNull
    @Override
    public String toString() {
        return "TransactionRequest{" +
                "requestId=" + requestId +
                ", cardNumberSource='" + maskCardNumber(cardNumberSource) + '\'' +
                ", cardNumberTarget='" + maskCardNumber(cardNumberTarget) + '\'' +
                ", sum=" + sum +
                '}';
    }
}
