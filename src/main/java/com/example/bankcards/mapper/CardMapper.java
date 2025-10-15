package com.example.bankcards.mapper;

import com.example.bankcards.dto.filter.CardSearchFilter;
import com.example.bankcards.dto.request.CardRequest;
import com.example.bankcards.dto.response.CardResponse;
import com.example.bankcards.entity.Card;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * Мапер для работы с сущностью карты и её моделями.
 */
@Mapper(componentModel = "spring")
public interface CardMapper {

    /**
     * Преобразование из запроса {@link CardRequest} в сущность {@link Card}.
     *
     * @param request запрос {@link CardRequest}
     * @return сущность {@link Card}
     */
    @Mapping(target = "id" , ignore = true)
    @Mapping(target = "owner" , ignore = true)
    @Mapping(target = "balance" , source = "balance", defaultValue = "0")
    @Mapping(target = "createdAt" , ignore = true)
    @Mapping(target = "updatedAt" , ignore = true)
    @Mapping(target = "blockRequest", ignore = true)
    Card toCard(@Nullable CardRequest request);

    /**
     * Преобразование из сущности {@link Card} в модель данных {@link CardResponse.CardInfo}.
     *
     * @param card сущность {@link Card}
     * @return модель данных {@link CardResponse.CardInfo}
     */
    @Mapping(target = "cardId", source = "id")
    @Mapping(target = "ownerUsername", source = "owner.username")
    @Mapping(target = "ownerFirstName", source = "owner.firstName")
    @Mapping(target = "ownerLastName", source = "owner.lastName")
    CardResponse.CardInfo toCardInfo(@Nullable Card card);

    /**
     * Преобразование из списка сущностей {@link Card} в список моделей данных {@link CardResponse.CardInfo}.
     *
     * @param cards список сущностей {@link Card}
     * @return список модель данных {@link CardResponse.CardInfo}
     */
    List<CardResponse.CardInfo> toCardInfoList(@Nullable List<Card> cards);

    /**
     * Преобразование из запроса {@link CardRequest} в фильтр для поиска {@link CardSearchFilter}.
     *
     * @param request запрос {@link CardRequest}
     * @return фильтр для поиска {@link CardSearchFilter}
     */
    CardSearchFilter toCardSearchFilter(@Nullable CardRequest request);
}
