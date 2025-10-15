package com.example.bankcards.controller;

import com.example.bankcards.dto.response.ErrorResponse;
import com.example.bankcards.dto.response.JwtResponse;
import com.example.bankcards.dto.response.Response;
import com.example.bankcards.security.service.AuthService;
import com.example.bankcards.security.service.JwtProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static com.example.bankcards.TestData.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AuthService authService;

    @MockitoBean
    private JwtProvider jwtProvider;

    @Test
    void loginTestSuccess() throws Exception {
        JwtResponse successResponse = successAuthResponse(LOGIN_REQUEST.getRequestId().toString());
        when(authService.login(any())).thenReturn(successResponse);
        doPostTest(LOGIN_URI, LOGIN_REQUEST, successResponse);
        verify(authService, times(1)).login(any());
    }

    @Test
    void loginTestBadRequest() throws Exception {
        ErrorResponse errorResponse = badRequestResponse("Логин пользователя не может быть пустым");
        doPostTest(LOGIN_URI, LOGIN_BAD_REQUEST, errorResponse);
        verify(authService, times(0)).login(any());
    }

    @Test
    void loginTestServerError() throws Exception {
        ErrorResponse errorResponse = serverErrorResponse("Сообщение об ошибке");
        when(authService.login(any())).thenThrow(new RuntimeException("Сообщение об ошибке"));
        doPostTest(LOGIN_URI, LOGIN_REQUEST_FOR_EXCEPTION, errorResponse);
        verify(authService, times(1)).login(any());
    }

    @Test
    void getNewAccessTokenTestSuccess() throws Exception {
        JwtResponse successResponse = successAuthResponse(GET_TOKEN_REQUEST.getRequestId().toString());
        when(authService.getAccessToken(any())).thenReturn(successResponse);
        doPostTest(ACCESS_TOKEN_URI, GET_TOKEN_REQUEST, successResponse);
        verify(authService, times(1)).getAccessToken(any());
    }

    @Test
    void getNewAccessTokenTestBadRequest() throws Exception {
        ErrorResponse errorResponse = badRequestResponse("Токен обновления не может быть пустым");
        doPostTest(ACCESS_TOKEN_URI, GET_TOKEN_BAD_REQUEST, errorResponse);
        verify(authService, times(0)).getAccessToken(any());
    }

    @Test
    void getNewAccessTokenTestServerError() throws Exception {
        ErrorResponse errorResponse = serverErrorResponse("Сообщение об ошибке");
        when(authService.getAccessToken(any())).thenThrow(new RuntimeException("Сообщение об ошибке"));
        doPostTest(ACCESS_TOKEN_URI, GET_TOKEN_REQUEST_FOR_EXCEPTION, errorResponse);
        verify(authService, times(1)).getAccessToken(any());
    }

    @Test
    void getNewRefreshTokenTestSuccess() throws Exception {
        JwtResponse successResponse = successAuthResponse(GET_TOKEN_REQUEST.getRequestId().toString());
        when(authService.refresh(any())).thenReturn(successResponse);
        doPostTest(REFRESH_TOKEN_URI, GET_TOKEN_REQUEST, successResponse);
        verify(authService, times(1)).refresh(any());
    }

    @Test
    void getNewRefreshTokenTestBadRequest() throws Exception {
        ErrorResponse errorResponse = badRequestResponse("Токен обновления не может быть пустым");
        doPostTest(REFRESH_TOKEN_URI, GET_TOKEN_BAD_REQUEST, errorResponse);
        verify(authService, times(0)).refresh(any());
    }

    @Test
    void getNewRefreshTokenTestServerError() throws Exception {
        ErrorResponse errorResponse = serverErrorResponse("Сообщение об ошибке");
        when(authService.refresh(any())).thenThrow(new RuntimeException("Сообщение об ошибке"));
        doPostTest(REFRESH_TOKEN_URI, GET_TOKEN_REQUEST_FOR_EXCEPTION, errorResponse);
        verify(authService, times(1)).refresh(any());
    }

    private void doPostTest(
            String uri,
            Object request,
            Response expectedResponse
    ) throws Exception {

        mockMvc.perform(
                        post(uri)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                                .accept(MediaType.APPLICATION_JSON)
                                .characterEncoding("utf-8")
                )
                .andExpect(
                        _ -> {
                            if (request.equals(LOGIN_REQUEST) ||
                                    request.equals(GET_TOKEN_REQUEST)) status().isOk();
                            if (request.equals(LOGIN_BAD_REQUEST) ||
                                    request.equals(GET_TOKEN_BAD_REQUEST)) status().isBadRequest();
                            if (request.equals(LOGIN_REQUEST_FOR_EXCEPTION) ||
                                    request.equals(GET_TOKEN_REQUEST_FOR_EXCEPTION))
                                status().isInternalServerError();
                        }
                )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));
    }
}
