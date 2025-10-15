package com.example.bankcards.service.impl;

import com.example.bankcards.constant.OperationStatus;
import com.example.bankcards.dto.request.TransactionRequest;
import com.example.bankcards.dto.response.TransactionResponse;
import com.example.bankcards.entity.Card;
import com.example.bankcards.service.DbService;
import com.example.bankcards.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final DbService dbService;

    @NonNull
    @Transactional
    @Override
    public TransactionResponse cardToCardTransfer(@NonNull TransactionRequest request) {
        Card cardSource = dbService.findCardByCardNumberLockCard(request.cardNumberSource());
        if (cardSource.getBalance().compareTo(request.sum()) < 0) {
            throw new IllegalStateException("Недостаточно средств");
        }
        cardSource.setBalance(cardSource.getBalance().subtract(request.sum()));
        Card cardTarget = dbService.findCardByCardNumberLockCard(request.cardNumberTarget());
        cardTarget.setBalance(cardTarget.getBalance().add(request.sum()));
        TransactionResponse transactionResponse = new TransactionResponse(
                OperationStatus.COMPLETED,
                request.getRequestId().toString(),
                cardSource.getCardNumber(),
                cardSource.getBalance(),
                cardTarget.getCardNumber(),
                cardTarget.getBalance(),
                request.sum()
        );
        log.debug("Перевод выполнен с результатом: {}", transactionResponse);
        return transactionResponse;
    }

    @NonNull
    @Transactional(readOnly = true)
    @Override
    public TransactionResponse cardBalance(@NonNull TransactionRequest request) {
        Card card = dbService.findCardByCardNumber(request.cardNumberTarget());
        TransactionResponse transactionResponse = new TransactionResponse(
                OperationStatus.COMPLETED,
                request.requestId().toString(),
                card.getCardNumber(),
                card.getBalance()
        );
        log.debug("Запрос баланса выполнен с результатом: {}", transactionResponse);
        return transactionResponse;
    }
}
