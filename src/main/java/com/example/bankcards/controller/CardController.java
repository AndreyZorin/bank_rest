package com.example.bankcards.controller;

import com.example.bankcards.controller.api.CardApi;
import com.example.bankcards.dto.request.CardRequest;
import com.example.bankcards.dto.response.CardResponse;
import com.example.bankcards.dto.response.Response;
import com.example.bankcards.service.CardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import static com.example.bankcards.util.LogUtils.logByLevelWithPayload;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CardController implements CardApi {

    private final CardService cardService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    public ResponseEntity<CardResponse> createCard(@NonNull CardRequest request) {
        logByLevelWithPayload("Получен запрос на создание карты", request);
        return ResponseEntity.ok(cardService.createCard(request));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    public ResponseEntity<CardResponse> updateCard(@NonNull CardRequest request) {
        logByLevelWithPayload("Получен запрос на обновление карты", request);
        return ResponseEntity.ok(cardService.updateCard(request));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    public ResponseEntity<Response> deleteCard(@NonNull CardRequest request) {
        logByLevelWithPayload("Получен запрос на удаление карты", request);
        return ResponseEntity.ok(cardService.deleteCard(request));
    }

    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('USER')")
    @PostAuthorize("@authorizationUtils.filterCards(returnObject)")
    @Override
    public ResponseEntity<CardResponse> searchCard(
            @NonNull CardRequest request,
            int pageNumber,
            int pageSize,
            @NonNull Sort.Direction direction,
            @NonNull String sortField
    ) {
        logByLevelWithPayload("Получен запрос на поиск карты", request);
        return ResponseEntity.ok(cardService.searchCard(request, pageNumber, pageSize, direction, sortField));
    }

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public ResponseEntity<Response> blockRequestCard(@NonNull CardRequest request) {
        logByLevelWithPayload("Получен запрос на блокировку карты от пользователя", request);
        return ResponseEntity.ok(cardService.blockRequestCard(request));
    }
}
