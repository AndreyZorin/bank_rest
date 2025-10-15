package com.example.bankcards.controller;

import com.example.bankcards.controller.api.AuthApi;
import com.example.bankcards.dto.request.JwtRequest;
import com.example.bankcards.dto.request.RefreshJwtRequest;
import com.example.bankcards.dto.response.JwtResponse;
import com.example.bankcards.security.service.AuthService;
import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RestController;

import static com.example.bankcards.util.LogUtils.logByLevelWithPayload;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi {

    private final AuthService authService;

    public ResponseEntity<JwtResponse> login(@NonNull JwtRequest request) throws AuthException {
        logByLevelWithPayload("Получен запрос на получение токена доступа и токена обновления по логину и паролю", request);
        JwtResponse token = authService.login(request);
        return ResponseEntity.ok(token);
    }

    public ResponseEntity<JwtResponse> getNewAccessToken(@NonNull RefreshJwtRequest request) {
        logByLevelWithPayload("Получен запрос на получение токена доступа по токену обновления", request);
        JwtResponse token = authService.getAccessToken(request);
        return ResponseEntity.ok(token);
    }

    public ResponseEntity<JwtResponse> getNewRefreshToken(@NonNull RefreshJwtRequest request) throws AuthException {
        logByLevelWithPayload("Получен запрос на получение токена и токена обновления доступа по токену обновления", request);
        JwtResponse token = authService.refresh(request);
        return ResponseEntity.ok(token);
    }

}
