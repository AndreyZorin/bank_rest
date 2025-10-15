package com.example.bankcards.dto.request;

import com.example.bankcards.constant.CardStatus;
import com.example.bankcards.util.validation.group.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import lombok.Getter;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static com.example.bankcards.util.MaskingUtils.maskCardNumber;

/**
 * Запрос на операции с картами.
 *
 * @param requestId идентификатор запроса
 * @param cardId идентификатор карты
 * @param cardNumber номер карты
 * @param ownerUsername ник владельца карты
 * @param ownerFullName полное имя владельца карты
 * @param userId идентификатор владельца карты
 * @param validityPeriod срок действия карты
 * @param status статус карты
 * @param balance баланс карты
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record CardRequest(

        @Getter
        @NotNull(message = "Идентификатор запроса не может быть пустым",
                groups = {CreateCard.class, UpdateCard.class, DeleteCard.class, SearchCard.class, BlockRequestCard.class})
        UUID requestId,

        Long cardId,

        @NotBlank(message = "Номер карты не может быть пустым",
                groups = {CreateCard.class, UpdateCard.class, DeleteCard.class, BlockRequestCard.class})
        @Pattern(
                regexp = "\\d{4}\\s\\d{4}\\s\\d{4}\\s\\d{4}",
                message = "Номер карты не соответствует паттерну",
                groups = {CreateCard.class, UpdateCard.class, DeleteCard.class, BlockRequestCard.class}
        )
        String cardNumber,

        @Size(max = 20, message = "В нике должно быть не больше 20 символов",
                groups = {SearchCard.class})
        String ownerUsername,

        String ownerFullName,

        @NotNull(message = "Идентификатор пользователя не может быть пустым",
                groups = {CreateCard.class})
        Long userId,

        @NotNull(message = "Срок действия карты не может быть пустым",
                groups = {CreateCard.class})
        LocalDate validityPeriod,

        @NotNull(message = "Статус карты не может быть пустым",
                groups = {CreateCard.class, UpdateCard.class})
        CardStatus status,

        @Digits(integer = 15, fraction = 2,
                groups = {CreateCard.class})
        BigDecimal balance
) {

    @NonNull
    @Override
    public String toString() {
        return "CardRequest{" +
                "requestId=" + requestId +
                ", cardId=" + cardId +
                ", cardNumber='" + maskCardNumber(cardNumber) + '\'' +
                ", ownerUsername='" + ownerUsername + '\'' +
                ", ownerFullName='" + ownerFullName + '\'' +
                ", userId=" + userId +
                ", validityPeriod=" + validityPeriod +
                ", status=" + status +
                ", balance=" + balance +
                '}';
    }
}
