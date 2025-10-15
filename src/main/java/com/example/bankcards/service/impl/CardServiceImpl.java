package com.example.bankcards.service.impl;

import com.example.bankcards.constant.OperationStatus;
import com.example.bankcards.dto.filter.CardSearchFilter;
import com.example.bankcards.dto.request.CardRequest;
import com.example.bankcards.dto.response.CardResponse;
import com.example.bankcards.dto.response.Response;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.User;
import com.example.bankcards.mapper.CardMapper;
import com.example.bankcards.service.CardService;
import com.example.bankcards.service.DbService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.bankcards.util.MaskingUtils.maskCardNumber;

@Slf4j
@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final DbService dbService;
    private final CardMapper cardMapper;

    @NonNull
    @Transactional
    @Override
    public CardResponse createCard(@NonNull CardRequest request) {
        Card card = cardMapper.toCard(request);
        User user = dbService.findUserById(request.userId());
        card.setOwner(user);
        Card savedCard = dbService.saveCard(card);
        CardResponse.CardInfo cardInfo = cardMapper.toCardInfo(savedCard);
        log.debug("Сохранена новая карта: {}", savedCard);
        return new CardResponse(
                OperationStatus.COMPLETED,
                request.getRequestId().toString(),
                List.of(cardInfo)
        );
    }

    @NonNull
    @Transactional
    @Override
    public CardResponse updateCard(@NonNull CardRequest request) {
        Card card = dbService.findCardByCardNumberLockCard(request.cardNumber());
        log.debug("Найдена карта для обновления: {}", card);
        card.setStatus(request.status());
        CardResponse.CardInfo cardInfo = cardMapper.toCardInfo(card);
        return new CardResponse(
                OperationStatus.COMPLETED,
                request.getRequestId().toString(),
                List.of(cardInfo)
        );
    }

    @NonNull
    @Transactional
    @Override
    public Response deleteCard(@NonNull CardRequest request) {
        dbService.deleteCardByCardNumber(request.cardNumber());
        if (dbService.existsCardByCardNumber(request.cardNumber())) {
            throw new IllegalStateException("Неудачная попытка удаления карты");
        }
        log.debug("Карта удалена: {}", maskCardNumber(request.cardNumber()));
        return new Response(
                OperationStatus.COMPLETED,
                request.requestId().toString()
        );
    }

    @NonNull
    @Transactional(readOnly = true)
    @Override
    public CardResponse searchCard(
            @NonNull CardRequest request,
            int pageNumber,
            int pageSize,
            @NonNull Sort.Direction direction,
            @NonNull String sortField
    ) {
        CardSearchFilter filter = cardMapper.toCardSearchFilter(request);
        Page<Card> page = dbService.searchCard(filter, pageNumber, pageSize, direction, sortField);
        List<CardResponse.CardInfo> cardInfoList = cardMapper.toCardInfoList(page.getContent());
        log.debug("Найдена очередная страница карт: {}", page);
        return new CardResponse(
                OperationStatus.COMPLETED,
                request.getRequestId().toString(),
                cardInfoList
        );
    }

    @NonNull
    @Transactional
    @Override
    public Response blockRequestCard(@NonNull CardRequest request) {
        Card card = dbService.findCardByCardNumberLockCard(request.cardNumber());
        card.setBlockRequest(true);
        log.debug("Запрос на блокировку карты {} сохранён", maskCardNumber(request.cardNumber()));
        return new Response(
                OperationStatus.COMPLETED,
                request.requestId().toString()
        );
    }
}
