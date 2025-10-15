package com.example.bankcards.repository.specification;

import com.example.bankcards.dto.filter.UserSearchFilter;
import com.example.bankcards.entity.User;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * Спецификации для расширенного поиска пользователей.
 */
@Component
public class UserSpecification {

    /**
     * Метод получения спецификации для расширенного поиска пользователей.
     *
     * @param filter фильтр для расширенного поиска карт
     * @return {@link Specification<User>}
     */
    @Nullable
    public Specification<User> getUserSpecification(@NonNull UserSearchFilter filter) {
        return Specification.allOf(
                        hasUserId(filter.userId()),
                        hasUsername(filter.username()),
                        hasFirstName(filter.firstName()),
                        hasLastName(filter.lastName())
                );
    }

    @Nullable
    private Specification<User> hasUserId(@Nullable Long userId) {
        if (ObjectUtils.isEmpty(userId)) {
            return null;
        }
        return (root, _, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("id"), userId);
    }

    @Nullable
    private Specification<User> hasUsername(@Nullable String username) {
        if (ObjectUtils.isEmpty(username)) {
            return null;
        }
        return (root, _, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("username"), username);
    }

    @Nullable
    private Specification<User> hasFirstName(@Nullable String firstName) {
        if (ObjectUtils.isEmpty(firstName)) {
            return null;
        }
        return (root, _, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("firstName"), firstName);
    }

    @Nullable
    private Specification<User> hasLastName(@Nullable String lastName) {
        if (ObjectUtils.isEmpty(lastName)) {
            return null;
        }
        return (root, _, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("lastName"), lastName);
    }
}
