package com.example.bankcards.service;

import com.example.bankcards.dto.request.CardRequest;
import com.example.bankcards.dto.response.CardResponse;
import com.example.bankcards.dto.response.Response;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;

/**
 * Сервис для работы с банковской картой пользователя.
 */
public interface CardService {

    /**
     * Создание карты.
     *
     * @param request запрос на создание карты
     * @return {@link CardResponse}
     */
    @NonNull
    CardResponse createCard(@NonNull CardRequest request);

    /**
     * Обновление карты.
     *
     * @param request запрос на обновление карты
     * @return {@link CardResponse}
     */
    @NonNull
    CardResponse updateCard(@NonNull CardRequest request);

    /**
     * Удаление карты.
     *
     * @param request запрос на удаление карты
     * @return {@link Response}
     */
    @NonNull
    Response deleteCard(@NonNull CardRequest request);

    /**
     * Поиск карты с фильтрацией, сортировкой и пагинацией.
     *
     * @param request запрос на поиск карты
     * @param pageNumber номер страницы
     * @param pageSize размер страницы
     * @param direction направление (ASC, DESC)
     * @param sortField поле сортировки
     * @return {@link CardResponse}
     */
    @NonNull
    CardResponse searchCard(
            @NonNull CardRequest request,
            int pageNumber,
            int pageSize,
            @NonNull Sort.Direction direction,
            @NonNull String sortField
    );

    /**
     * Запрос на блокировку карты от пользователя.
     *
     * @param request запрос на блокировку карты от пользователя
     * @return {@link Response}
     */
    @NonNull
    Response blockRequestCard(@NonNull CardRequest request);
}
