package com.example.bankcards.security.service;

import com.example.bankcards.entity.User;
import com.example.bankcards.service.DbService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Реализация загрузчика данных пользователя.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthUserDetailsService implements UserDetailsService {

    private final DbService dbService;

    /**
     * Получение данных пользователя.
     *
     * @param username ник пользователя.
     * @return {@link User}
     * @throws UsernameNotFoundException ошибка поиска пользователя
     */
    @NonNull
    @Transactional(readOnly = true)
    @Override
    public User loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        User user = dbService.findUserByUsername(username);
        log.debug("Пользователь найден по нику {}: {}", username, user);
        return user;
    }
}
