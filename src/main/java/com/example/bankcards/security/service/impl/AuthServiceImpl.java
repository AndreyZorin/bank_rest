package com.example.bankcards.security.service.impl;

import com.example.bankcards.constant.OperationStatus;
import com.example.bankcards.dto.request.RefreshJwtRequest;
import com.example.bankcards.entity.User;
import com.example.bankcards.security.model.JwtAuthentication;
import com.example.bankcards.dto.request.JwtRequest;
import com.example.bankcards.dto.response.JwtResponse;
import com.example.bankcards.security.service.AuthService;
import com.example.bankcards.security.service.AuthUserDetailsService;
import com.example.bankcards.security.service.JwtProvider;
import io.jsonwebtoken.Claims;
import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthUserDetailsService userDetailsService;
    private final Map<String, String> refreshStorage = new HashMap<>();
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @NonNull
    public JwtResponse login(@NonNull JwtRequest authRequest) throws AuthException {
        final User user = userDetailsService.loadUserByUsername(authRequest.getLogin());
        if (passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            final String accessToken = jwtProvider.generateAccessToken(user);
            final String refreshToken = jwtProvider.generateRefreshToken(user);
            System.out.println(user.getUsername());
            System.out.println(refreshToken);
            refreshStorage.put(user.getUsername(), refreshToken);
            log.info("Аутентификация пройдена");
            return new JwtResponse(OperationStatus.COMPLETED, authRequest.getRequestId().toString(), accessToken, refreshToken);
        } else {
            throw new AuthException("Неправильный пароль");
        }
    }

    @NonNull
    public JwtResponse getAccessToken(@NonNull RefreshJwtRequest request) {
        if (jwtProvider.validateRefreshToken(request.getRefreshToken())) {
            final Claims claims = jwtProvider.getRefreshClaims(request.getRefreshToken());
            final String login = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(login);
            if (saveRefreshToken != null && saveRefreshToken.equals(request.getRefreshToken())) {
                final User user = userDetailsService.loadUserByUsername(login);
                final String accessToken = jwtProvider.generateAccessToken(user);
                return new JwtResponse(OperationStatus.COMPLETED, request.getRequestId().toString(), accessToken, null);
            }
        }
        return new JwtResponse(OperationStatus.ERROR, "not_defined", null, null);
    }

    @NonNull
    public JwtResponse refresh(@NonNull RefreshJwtRequest request) throws AuthException {
        if (jwtProvider.validateRefreshToken(request.getRefreshToken())) {
            final Claims claims = jwtProvider.getRefreshClaims(request.getRefreshToken());
            final String login = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(login);
            if (saveRefreshToken != null && saveRefreshToken.equals(request.getRefreshToken())) {
                final User user = userDetailsService.loadUserByUsername(login);
                final String accessToken = jwtProvider.generateAccessToken(user);
                final String newRefreshToken = jwtProvider.generateRefreshToken(user);
                refreshStorage.put(user.getUsername(), newRefreshToken);
                return new JwtResponse(OperationStatus.COMPLETED, request.getRequestId().toString(), accessToken, newRefreshToken);
            }
        }
        throw new AuthException("Невалидный JWT токен");
    }

    @NonNull
    public JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }
}
