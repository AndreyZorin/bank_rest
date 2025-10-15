package com.example.bankcards.repository;

import com.example.bankcards.entity.Card;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.*;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий для работы с сущностью {@link Card}.
 */
@Repository
public interface CardRepository extends JpaRepository<Card, Long>, JpaSpecificationExecutor<Card> {

    /**
     * Получение карты по номеру карты с блокировкой.
     *
     * @param cardNumber номер карты
     * @return {@link Card}
     */
    @NonNull
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints(value = {@QueryHint(name = "jakarta.persistence.lock.timeout", value = "3000")})
    Optional<Card> findCardByCardNumber(@NonNull String cardNumber);

    /**
     * Получение карты с владельцем карты по номеру карты без блокировки.
     *
     * @param cardNumber номер карты
     * @return {@link Card}
     */
    @NonNull
    @EntityGraph("card-with-owner")
    Optional<Card> findByCardNumber(@NonNull String cardNumber);

    /**
     * Удаление карты по номеру карты.
     *
     * @param cardNumber номер карты
     */
    void deleteCardByCardNumber(@NonNull String cardNumber);

    boolean existsCardByCardNumber(@NonNull String cardNumber);

    /**
     * Расширенный поиск карт с фильтрацией и пагинацией.
     *
     * @param spec спецификация для карты
     * @param pageable объект постраничного вывода
     * @return {@link Page<Card>}
     */
    @NonNull
    @EntityGraph("card-with-owner")
    Page<Card> findAll(@Nullable Specification<Card> spec, @NonNull Pageable pageable);
}
