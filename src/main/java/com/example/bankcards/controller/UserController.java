package com.example.bankcards.controller;

import com.example.bankcards.controller.api.UserApi;
import com.example.bankcards.dto.response.Response;
import com.example.bankcards.dto.request.UserRequest;
import com.example.bankcards.dto.response.UserResponse;
import com.example.bankcards.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import static com.example.bankcards.util.LogUtils.logByLevelWithPayload;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    public ResponseEntity<UserResponse> createUser(@NonNull UserRequest request) {
        logByLevelWithPayload("Получен запрос на создание пользователя", request);
        return ResponseEntity.ok(userService.createUser(request));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    public ResponseEntity<UserResponse> updateUser(@NonNull UserRequest request) {
        logByLevelWithPayload("Получен запрос на обновление пользователя", request);
        return ResponseEntity.ok(userService.updateUser(request));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    public ResponseEntity<Response> deleteUser(@NonNull UserRequest request) {
        logByLevelWithPayload("Получен запрос на удаление пользователя", request);
        return ResponseEntity.ok(userService.deleteUser(request));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    public ResponseEntity<UserResponse> searchUser(
            @NonNull UserRequest request,
            int pageNumber,
            int pageSize,
            @NonNull Sort.Direction direction,
            @NonNull String sortField) {
        logByLevelWithPayload("Получен запрос на поиск пользователя", request);
        return ResponseEntity.ok(userService.searchUser(request, pageNumber, pageSize, direction, sortField));
    }
}
