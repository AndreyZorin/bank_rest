package com.example.bankcards.repository.specification;

import com.example.bankcards.constant.CardStatus;
import com.example.bankcards.dto.filter.CardSearchFilter;
import com.example.bankcards.entity.Card;
import jakarta.persistence.criteria.Path;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * Спецификации для расширенного поиска карт.
 */
@Component
public class CardSpecification {

    /**
     * Метод получения спецификации для расширенного поиска карт.
     *
     * @param filter фильтр для расширенного поиска карт
     * @return {@link Specification<Card>}
     */
    @Nullable
    public Specification<Card> getCardSpecification(@NonNull CardSearchFilter filter) {
        return Specification.allOf(
                        hasCardId(filter.cardId()),
                        hasCardNumber(filter.cardNumber()),
                        hasCardStatus(filter.status()),
                        hasOwnerUsername(filter.ownerUsername()),
                        hasOwnerFullName(filter.ownerFullName())
                );
    }

    @Nullable
    private Specification<Card> hasCardId(@Nullable Long cardId) {
        if (ObjectUtils.isEmpty(cardId)) {
            return null;
        }
        return (root, _, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("id"), cardId);
    }

    @Nullable
    private Specification<Card> hasCardNumber(@Nullable String cardNumber) {
        if (ObjectUtils.isEmpty(cardNumber)) {
            return null;
        }
        return (root, _, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("cardNumber"), cardNumber);
    }

    @Nullable
    private Specification<Card> hasCardStatus(@Nullable CardStatus status) {
        if (ObjectUtils.isEmpty(status)) {
            return null;
        }
        return (root, _, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("status"), status);
    }

    @Nullable
    private Specification<Card> hasOwnerUsername(@Nullable String username) {
        if (ObjectUtils.isEmpty(username)) {
            return null;
        }
        return (root, _, criteriaBuilder) -> {
            Path<Object> path = root.get("owner");
            if (path == null) {
                return null;
            }
            return criteriaBuilder.equal(path.get("username"), username);
        };
    }

    @Nullable
    private Specification<Card> hasOwnerFullName(@Nullable String fullName) {
        if (ObjectUtils.isEmpty(fullName)) {
            return null;
        }
        return (root, _, criteriaBuilder) -> {
            Path<Object> path = root.get("owner");
            if (path == null) {
                return null;
            }
            String[] fullNameArray = fullName.split(" ");
            String firstName = fullNameArray[0];
            String lastName = fullNameArray[1];
            return criteriaBuilder.and(
                    criteriaBuilder.equal(path.get("firstName"), firstName),
                    criteriaBuilder.equal(path.get("lastName"), lastName)
            );
        };
    }
}
