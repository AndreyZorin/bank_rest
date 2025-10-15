package com.example.bankcards.security.util;

import com.example.bankcards.dto.request.TransactionRequest;
import com.example.bankcards.dto.response.CardResponse;
import com.example.bankcards.entity.Card;
import com.example.bankcards.security.model.JwtAuthentication;
import com.example.bankcards.security.service.AuthService;
import com.example.bankcards.service.DbService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

/**
 * Функции для авторизации.
 */
@Component
@RequiredArgsConstructor
public class AuthorizationUtils {

    private final AuthService authService;
    private final DbService dbService;

    /**
     * Валидация владельца карты.
     *
     * @param request запрос на транзакцию
     * @return результат валидации
     */
    public boolean isCardsOwner(@NonNull TransactionRequest request) {
        String username = authService.getAuthInfo().getUsername();
        Card cardSource = (request.cardNumberSource() != null) ? dbService.findCardByCardNumber(request.cardNumberSource()) : null;
        Card cardTarget = (request.cardNumberTarget() != null) ? dbService.findCardByCardNumber(request.cardNumberTarget()) : null;
        return (cardSource == null || cardSource.getOwner().getUsername().equals(username)) &&
                (cardTarget == null || cardTarget.getOwner().getUsername().equals(username));
    }

    /**
     * Фильтрация карт по владельцу.
     *
     * @param response ответ с данными карт для фильтрации
     * @return результат фильтрации
     */
    public boolean filterCards(@NonNull ResponseEntity<CardResponse> response) {
        JwtAuthentication authInfo = authService.getAuthInfo();
        String username = authInfo.getUsername();
        Collection<? extends GrantedAuthority> authorities = authInfo.getAuthorities();
        CardResponse cardResponse = response.getBody();
        if (
                authorities.stream().map(GrantedAuthority::getAuthority).noneMatch(a -> a.equals("ADMIN")) &&
                cardResponse != null &&
                cardResponse.getCardInfoList() != null
        ) {
            List<CardResponse.CardInfo> filteredCardInfoList = cardResponse.getCardInfoList().stream()
                    .filter(cardInfo -> cardInfo.getOwnerUsername().equals(username))
                    .toList();
            cardResponse.setCardInfoList(filteredCardInfoList);
        }
        return true;
    }
}
