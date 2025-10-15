package com.example.bankcards.controller;

import com.example.bankcards.controller.api.TransactionApi;
import com.example.bankcards.dto.request.TransactionRequest;
import com.example.bankcards.dto.response.TransactionResponse;
import com.example.bankcards.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import static com.example.bankcards.util.LogUtils.logByLevelWithPayload;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TransactionController implements TransactionApi {

    private final TransactionService transactionService;

    @PreAuthorize("hasAuthority('USER') && @authorizationUtils.isCardsOwner(#request)")
    @Override
    public ResponseEntity<TransactionResponse> cardToCardTransfer(@NonNull TransactionRequest request) {
        logByLevelWithPayload("Получен запрос на перевод между картами пользователя", request);
        return ResponseEntity.ok(transactionService.cardToCardTransfer(request));
    }

    @PreAuthorize("hasAuthority('USER') && @authorizationUtils.isCardsOwner(#request)")
    @Override
    public ResponseEntity<TransactionResponse> cardBalance(@NonNull TransactionRequest request) {
        logByLevelWithPayload("Получен запрос на получение баланса карты", request);
        return ResponseEntity.ok(transactionService.cardBalance(request));
    }
}
