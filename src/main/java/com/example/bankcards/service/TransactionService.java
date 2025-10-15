package com.example.bankcards.service;

import com.example.bankcards.dto.request.TransactionRequest;
import com.example.bankcards.dto.response.TransactionResponse;
import org.springframework.lang.NonNull;

/**
 * Сервис для работы с денежными транзакциями.
 */
public interface TransactionService {

    /**
     * Перевод средств между счетами пользователя.
     *
     * @param request запрос на перевод средств между счетами пользователя
     * @return {@link TransactionResponse}
     */
    @NonNull
    TransactionResponse cardToCardTransfer(@NonNull TransactionRequest request);

    /**
     * Запрос на проверку баланса карты пользователя.
     *
     * @param request запрос баланса карты пользователя
     * @return {@link TransactionResponse}
     */
    @NonNull
    TransactionResponse cardBalance(@NonNull TransactionRequest request);
}
