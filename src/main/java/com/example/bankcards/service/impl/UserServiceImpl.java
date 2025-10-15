package com.example.bankcards.service.impl;

import com.example.bankcards.constant.OperationStatus;
import com.example.bankcards.constant.RoleName;
import com.example.bankcards.dto.filter.UserSearchFilter;
import com.example.bankcards.dto.response.Response;
import com.example.bankcards.dto.request.RoleRequest;
import com.example.bankcards.dto.request.UserRequest;
import com.example.bankcards.dto.response.UserResponse;
import com.example.bankcards.entity.Role;
import com.example.bankcards.entity.User;
import com.example.bankcards.mapper.UserMapper;
import com.example.bankcards.service.DbService;
import com.example.bankcards.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final DbService dbService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @NonNull
    @Transactional
    @Override
    public UserResponse createUser(@NonNull UserRequest request) {
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.password()));
        Set<RoleName> roleNames =  request.roles().stream().map(RoleRequest::name).collect(Collectors.toSet());
        Set<Role> roleSet = dbService.findRolesByNames(roleNames);
        user.setRoles(roleSet);
        User savedUser = dbService.saveUser(user);
        UserResponse.UserInfo userInfo = userMapper.toUserInfo(savedUser);
        log.debug("Сохранён новый пользователь: {}", savedUser);
        return new UserResponse(
                OperationStatus.COMPLETED,
                request.getRequestId().toString(),
                List.of(userInfo)
        );
    }

    @NonNull
    @Transactional
    @Override
    public UserResponse updateUser(@NonNull UserRequest request) {
        User user = dbService.findUserByIdLockUser(request.userId());
        log.debug("Найден пользователь для обновления: {}", user);
        updateUserData(user, request);
        UserResponse.UserInfo userInfo = userMapper.toUserInfo(user);
        return new UserResponse(
                OperationStatus.COMPLETED,
                request.getRequestId().toString(),
                List.of(userInfo)
        );
    }

    @NonNull
    @Transactional
    @Override
    public Response deleteUser(@NonNull UserRequest request) {
        dbService.deleteUserById(request.userId());
        if (dbService.existsUserById(request.userId())) {
            throw new IllegalStateException("Неудачная попытка удаления пользователя");
        }
        log.debug("Пользователь удалён: {}", request.userId());
        return new Response(
                OperationStatus.COMPLETED,
                request.requestId().toString()
        );
    }

    @NonNull
    @Transactional(readOnly = true)
    @Override
    public UserResponse searchUser(
            @NonNull UserRequest request,
            int pageNumber,
            int pageSize,
            @NonNull Sort.Direction direction,
            @NonNull String sortField
    ) {
        UserSearchFilter filter = userMapper.toUserSearchFilter(request);
        Page<User> page = dbService.searchUser(filter, pageNumber, pageSize, direction, sortField);
        List<UserResponse.UserInfo> userInfoList = userMapper.toUserInfoList(page.getContent());
        log.debug("Найдена очередная страница пользователей: {}", page);
        return new UserResponse(
                OperationStatus.COMPLETED,
                request.getRequestId().toString(),
                userInfoList
        );
    }

    private void updateUserData(User user, UserRequest request) {
        if (request.roles() != null && !request.roles().isEmpty()) {
            Set<RoleName> roleNames =  request.roles().stream().map(RoleRequest::name).collect(Collectors.toSet());
            Set<Role> roleSet = dbService.findRolesByNames(roleNames);
            user.setRoles(roleSet);
        }
        if (request.username() != null) {
            user.setUsername(request.username());
        }
    }
}
