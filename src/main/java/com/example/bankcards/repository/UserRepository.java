package com.example.bankcards.repository;

import com.example.bankcards.entity.User;
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

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    /**
     * Получение пользователя по идентификатору с блокировкой.
     *
     * @param id идентификатор пользователя
     * @return {@link Optional<User>}
     */
    @Override
    @NonNull
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints(value = {@QueryHint(name = "jakarta.persistence.lock.timeout", value = "3000")})
    Optional<User> findById(@NonNull Long id);

    /**
     * Получение пользователя с ролями по нику.
     *
     * @param username ник пользователя
     * @return {@link Optional<User>}
     */
    @NonNull
    @EntityGraph("user-with-roles")
    Optional<User> findByUsername(@NonNull String username);

    /**
     * Получение пользователя по идентификатору без блокировки.
     *
     * @param id идентификатор пользователя
     * @return {@link Optional<User>}
     */
    Optional<User> findUserById(Long id);

    /**
     * Расширенный поиск пользователей с фильтрацией и пагинацией.
     *
     * @param spec спецификация для пользователя
     * @param pageable объект постраничного вывода
     * @return {@link Page<User>}
     */
    @NonNull
    @EntityGraph("user-with-roles")
    Page<User> findAll(@Nullable Specification<User> spec, @NonNull Pageable pageable);
}
