package com.example.bankcards.service;

import com.example.bankcards.constant.RoleName;
import com.example.bankcards.dto.filter.CardSearchFilter;
import com.example.bankcards.dto.filter.UserSearchFilter;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.Role;
import com.example.bankcards.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;

import java.util.Set;

/**
 * Сервис для работы с БД.
 */
public interface DbService {

    /**
     * Сохранение пользователя.
     *
     * @param user пользователь
     * @return {@link User}
     */
    @NonNull
    User saveUser(@NonNull User user);

    /**
     * Получение пользователя по идентификатору с блокировкой.
     *
     * @param id идентификатор пользователя
     * @return {@link User}
     */
    @NonNull
    User findUserByIdLockUser(@NonNull Long id);

    /**
     * Получение пользователя по идентификатору.
     *
     * @param id идентификатор пользователя
     * @return {@link User}
     */
    @NonNull
    User findUserById(@NonNull Long id);

    /**
     * Получение пользователя по нику.
     *
     * @param username ник пользователя
     * @return {@link User}
     */
    @NonNull
    User findUserByUsername(@NonNull String username);

    /**
     * Удаление пользователя по идентификатору.
     *
     * @param id идентификатор пользователя
     */
    void deleteUserById(@NonNull Long id);

    /**
     * Проверка существования записи о пользователе в БД по идентификатору.
     *
     * @param id идентификатор пользователя
     * @return результат проверки
     */
    boolean existsUserById(@NonNull Long id);

    /**
     * Поиск пользователя с фильтрацией, сортировкой и пагинацией.
     *
     * @param filter фильтр для поиска пользователя
     * @param pageNumber номер страницы
     * @param pageSize размер страницы
     * @param direction направление (ASC, DESC)
     * @param sortField поле сортировки
     * @return {@link Page<User>}
     */
    @NonNull
    Page<User> searchUser(
            @NonNull UserSearchFilter filter,
            int pageNumber,
            int pageSize,
            @NonNull Sort.Direction direction,
            @NonNull String sortField
    );

    /**
     * Получение ролей по множеству имён.
     *
     * @param names имена ролей
     * @return {@link Set<Role>}
     */
    @NonNull
    Set<Role> findRolesByNames(@NonNull Set<RoleName> names);

    /**
     * Сохранение карты.
     *
     * @param card карта пользователя
     * @return {@link Card}
     */
    @NonNull
    Card saveCard(@NonNull Card card);

    /**
     * Получение карты по номеру карты с блокировкой.
     *
     * @param cardNumber номер карты
     * @return {@link Card}
     */
    @NonNull
    Card findCardByCardNumberLockCard(@NonNull String cardNumber);

    /**
     * Получение карты по номеру карты.
     *
     * @param cardNumber номер карты
     * @return {@link Card}
     */
    @NonNull
    Card findCardByCardNumber(@NonNull String cardNumber);

    /**
     * Удаление карты по номеру карты.
     *
     * @param cardNumber номер карты
     */
    void deleteCardByCardNumber(@NonNull String cardNumber);

    /**
     * Проверка на наличие записи о карте в БД.
     *
     * @param cardNumber номер карты
     * @return результат проверки
     */
    boolean existsCardByCardNumber(@NonNull String cardNumber);

    /**
     * Поиск карты с фильтрацией, сортировкой и пагинацией.
     *
     * @param filter фильтр для поиска карты
     * @param pageNumber номер страницы
     * @param pageSize размер страницы
     * @param direction направление (ASC, DESC)
     * @param sortField поле сортировки
     * @return {@link Page<Card>}
     */
    @NonNull
    Page<Card> searchCard(
            @NonNull CardSearchFilter filter,
            int pageNumber,
            int pageSize,
            @NonNull Sort.Direction direction,
            @NonNull String sortField
    );
}
