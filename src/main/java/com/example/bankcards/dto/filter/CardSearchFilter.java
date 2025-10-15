package com.example.bankcards.dto.filter;

import com.example.bankcards.constant.CardStatus;

/**
 * Фильтр для расширенного поиска карт.
 *
 * @param cardId идентификатор карты
 * @param cardNumber номер карты
 * @param ownerUsername ник владельца карты
 * @param ownerFullName полное имя владельца карты
 * @param status статус карты
 */
public record CardSearchFilter(

        Long cardId,
        String cardNumber,
        String ownerUsername,
        String ownerFullName,
        CardStatus status
) {
}
