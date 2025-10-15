package com.example.bankcards.service;

import com.example.bankcards.dto.request.JwtRequest;
import com.example.bankcards.dto.response.JwtResponse;
import com.example.bankcards.entity.User;
import com.example.bankcards.security.service.AuthUserDetailsService;
import com.example.bankcards.security.service.JwtProvider;
import com.example.bankcards.security.service.impl.AuthServiceImpl;
import io.jsonwebtoken.Claims;
import jakarta.security.auth.message.AuthException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.example.bankcards.TestData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTests {

    @InjectMocks
    private AuthServiceImpl subject;

    @Mock
    private AuthUserDetailsService userDetailsService;
    @Mock
    private JwtProvider jwtProvider;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void loginSuccessTest() throws AuthException {
        User user = new User();
        user.setUsername("test_user");

        when(userDetailsService.loadUserByUsername(any())).thenReturn(user);
        when(passwordEncoder.matches(any(), any())).thenReturn(true);
        when(jwtProvider.generateAccessToken(any())).thenReturn(TEST_ACCESS_TOKEN);
        when(jwtProvider.generateRefreshToken(any())).thenReturn(TEST_REFRESH_TOKEN);

        JwtResponse jwtResponse = subject.login(LOGIN_REQUEST);
        assertEquals(TEST_ACCESS_TOKEN, jwtResponse.getAccessToken());
        assertEquals(TEST_REFRESH_TOKEN, jwtResponse.getRefreshToken());

        verify(userDetailsService, times(1)).loadUserByUsername(any());
        verify(passwordEncoder, times(1)).matches(any(), any());
        verify(jwtProvider, times(1)).generateAccessToken(any());
        verify(jwtProvider, times(1)).generateRefreshToken(any());
    }

    @Test
    void loginFailureWrongPasswordTest() {
        User user = mock(User.class);

        when(userDetailsService.loadUserByUsername(any())).thenReturn(user);
        when(passwordEncoder.matches(any(), any())).thenReturn(false);

        String message = assertThrows(AuthException.class, () -> subject.login(LOGIN_REQUEST))
                .getMessage();
        assertEquals("Неправильный пароль", message);

        verify(userDetailsService, times(1)).loadUserByUsername(any());
        verify(passwordEncoder, times(1)).matches(any(), any());
        verify(jwtProvider, times(0)).generateAccessToken(any());
        verify(jwtProvider, times(0)).generateRefreshToken(any());
    }

    @Test
    void loginFailureNotFoundTest() {
        JwtRequest request = mock(JwtRequest.class);

        when(userDetailsService.loadUserByUsername(any()))
                .thenThrow(new UsernameNotFoundException(("Пользователь не найден")));

        String message = assertThrows(UsernameNotFoundException.class, () -> subject.login(request))
                .getMessage();
        assertEquals(("Пользователь не найден"), message);

        verify(userDetailsService, times(1)).loadUserByUsername(any());
        verify(passwordEncoder, times(0)).matches(any(), any());
        verify(jwtProvider, times(0)).generateAccessToken(any());
        verify(jwtProvider, times(0)).generateRefreshToken(any());
    }

    @Test
    void getAccessTokenSuccessTest() throws AuthException {
        loginSuccessTest();

        User user = mock(User.class);
        Claims claims = getTestRefreshClaims();

        when(userDetailsService.loadUserByUsername(any())).thenReturn(user);
        when(jwtProvider.validateRefreshToken(any())).thenReturn(true);
        when(jwtProvider.getRefreshClaims(any())).thenReturn(claims);
        when(jwtProvider.generateAccessToken(any())).thenReturn(TEST_ACCESS_TOKEN);

        JwtResponse jwtResponse = subject.getAccessToken(GET_TOKEN_REQUEST);

        assertEquals(TEST_ACCESS_TOKEN, jwtResponse.getAccessToken());

        verify(userDetailsService, times(2)).loadUserByUsername(any());
        verify(jwtProvider, times(1)).validateRefreshToken(any());
        verify(jwtProvider, times(2)).generateAccessToken(any());
        verify(jwtProvider, times(1)).getRefreshClaims(any());
    }

    @Test
    void getAccessTokenReturnNullTest() {
        when(jwtProvider.validateRefreshToken(any())).thenReturn(false);

        JwtResponse jwtResponse = subject.getAccessToken(GET_TOKEN_REQUEST);

        assertNull(jwtResponse.getAccessToken());

        verify(userDetailsService, times(0)).loadUserByUsername(any());
        verify(jwtProvider, times(1)).validateRefreshToken(any());
        verify(jwtProvider, times(0)).generateAccessToken(any());
        verify(jwtProvider, times(0)).getRefreshClaims(any());
    }

    @Test
    void refreshSuccessTest() throws AuthException {
        loginSuccessTest();

        User user = mock(User.class);
        Claims claims = getTestRefreshClaims();

        when(userDetailsService.loadUserByUsername(any())).thenReturn(user);
        when(jwtProvider.validateRefreshToken(any())).thenReturn(true);
        when(jwtProvider.getRefreshClaims(any())).thenReturn(claims);
        when(jwtProvider.generateAccessToken(any())).thenReturn(TEST_ACCESS_TOKEN);
        when(jwtProvider.generateRefreshToken(any())).thenReturn(TEST_REFRESH_TOKEN);

        JwtResponse jwtResponse = subject.refresh(GET_TOKEN_REQUEST);

        assertEquals(TEST_ACCESS_TOKEN, jwtResponse.getAccessToken());
        assertEquals(TEST_REFRESH_TOKEN, jwtResponse.getRefreshToken());

        verify(userDetailsService, times(2)).loadUserByUsername(any());
        verify(jwtProvider, times(1)).validateRefreshToken(any());
        verify(jwtProvider, times(2)).generateAccessToken(any());
        verify(jwtProvider, times(2)).generateRefreshToken(any());
        verify(jwtProvider, times(1)).getRefreshClaims(any());
    }

    @Test
    void refreshFailureInvalidTokenTest() {
        when(jwtProvider.validateRefreshToken(any())).thenReturn(false);

        String message = assertThrows(AuthException.class, () -> subject.refresh(GET_TOKEN_REQUEST))
                .getMessage();
        assertEquals("Невалидный JWT токен", message);

        verify(jwtProvider, times(1)).validateRefreshToken(any());
    }
}
