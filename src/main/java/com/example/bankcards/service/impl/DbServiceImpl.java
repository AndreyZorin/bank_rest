package com.example.bankcards.service.impl;

import com.example.bankcards.constant.RoleName;
import com.example.bankcards.dto.filter.CardSearchFilter;
import com.example.bankcards.dto.filter.UserSearchFilter;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.Role;
import com.example.bankcards.entity.User;
import com.example.bankcards.exception.ObjectNotFoundException;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.RoleRepository;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.repository.specification.CardSpecification;
import com.example.bankcards.repository.specification.UserSpecification;
import com.example.bankcards.service.DbService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DbServiceImpl implements DbService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CardRepository cardRepository;
    private final UserSpecification userSpecification;
    private final CardSpecification cardSpecification;

    @NonNull
    @Override
    public User saveUser(@NonNull User user) {
        return userRepository.save(user);
    }

    @NonNull
    @Override
    public User findUserByIdLockUser(@NonNull Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new ObjectNotFoundException(String.format("Пользователь с идентификкатором %d не найден", id))
        );
    }

    @NonNull
    @Override
    public User findUserById(@NonNull Long id) {
        return userRepository.findUserById(id).orElseThrow(
                () -> new ObjectNotFoundException(String.format("Пользователь с идентификкатором %d не найден", id))
        );
    }

    @NonNull
    @Override
    public User findUserByUsername(@NonNull String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new ObjectNotFoundException(String.format("Пользователь с ником %s не найден", username))
        );
    }

    @Override
    public void deleteUserById(@NonNull Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean existsUserById(@NonNull Long id) {
        return userRepository.existsById(id);
    }

    @NonNull
    @Override
    public Page<User> searchUser(
            @NonNull UserSearchFilter filter,
            int pageNumber,
            int pageSize,
            @NonNull Sort.Direction direction,
            @NonNull String sortField
    ) {
        return userRepository.findAll(
                userSpecification.getUserSpecification(filter),
                PageRequest.of(pageNumber, pageSize, Sort.by(direction, sortField)));
    }

    @NonNull
    @Override
    public Set<Role> findRolesByNames(@NonNull Set<RoleName> names) {
        Set<Role> roles = roleRepository.findAllByNameIn(names);
        if (roles.isEmpty()) {
            throw new ObjectNotFoundException(
                    String.format("Роли с именами %s не найдены",
                            names.stream().map(Enum::name).collect(Collectors.joining(", ")))
            );
        }
        return roles;
    }

    @NonNull
    @Override
    public Card saveCard(@NonNull Card card) {
        return cardRepository.save(card);
    }

    @NonNull
    @Override
    public Card findCardByCardNumberLockCard(@NonNull String cardNumber) {
        return cardRepository.findCardByCardNumber(cardNumber).orElseThrow(
                () -> new ObjectNotFoundException(String.format("Карта с номером %s не найдена", cardNumber))
        );
    }

    @NonNull
    @Override
    public Card findCardByCardNumber(@NonNull String cardNumber) {
        return cardRepository.findByCardNumber(cardNumber).orElseThrow(
                () -> new ObjectNotFoundException(String.format("Карта с номером %s не найдена", cardNumber))
        );
    }

    @Override
    public void deleteCardByCardNumber(@NonNull String cardNumber) {
        cardRepository.deleteCardByCardNumber(cardNumber);
    }

    @Override
    public boolean existsCardByCardNumber(@NonNull String cardNumber) {
        return cardRepository.existsCardByCardNumber(cardNumber);
    }

    @NonNull
    @Override
    public Page<Card> searchCard(
            @NonNull CardSearchFilter filter,
            int pageNumber,
            int pageSize,
            @NonNull Sort.Direction direction,
            @NonNull String sortField
    ) {
        return cardRepository.findAll(
                cardSpecification.getCardSpecification(filter),
                PageRequest.of(pageNumber, pageSize, Sort.by(direction, sortField)));
    }
}
