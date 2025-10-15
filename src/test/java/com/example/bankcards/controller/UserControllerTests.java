package com.example.bankcards.controller;

import com.example.bankcards.dto.response.ErrorResponse;
import com.example.bankcards.dto.response.Response;
import com.example.bankcards.dto.response.UserResponse;
import com.example.bankcards.security.service.JwtProvider;
import com.example.bankcards.service.UserService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private JwtProvider jwtProvider;

    @Test
    void createUserTestSuccess() throws Exception {
        UserResponse successResponse = successUserResponse(CREATE_USER_REQUEST.getRequestId().toString());
        when(userService.createUser(any())).thenReturn(successResponse);
        doPostTest(USER_BASE_URI, CREATE_USER_REQUEST, successResponse);
        verify(userService, times(1)).createUser(any());
    }

    @Test
    void createUserTestBadRequest() throws Exception {
        ErrorResponse errorResponse = badRequestResponse("Ник пользователя не может быть пустым");
        doPostTest(USER_BASE_URI, CREATE_USER_BAD_REQUEST, errorResponse);
        verify(userService, times(0)).createUser(any());
    }

    @Test
    void createUserTestServerError() throws Exception {
        ErrorResponse errorResponse = serverErrorResponse("Сообщение об ошибке");
        when(userService.createUser(any())).thenThrow(new RuntimeException("Сообщение об ошибке"));
        doPostTest(USER_BASE_URI, CREATE_USER_REQUEST_FOR_EXCEPTION, errorResponse);
        verify(userService, times(1)).createUser(any());
    }

    @Test
    void updateUserTestSuccess() throws Exception {
        UserResponse successResponse = successUserResponse(UPDATE_USER_REQUEST.getRequestId().toString());
        when(userService.updateUser(any())).thenReturn(successResponse);
        doPutTest(USER_BASE_URI, UPDATE_USER_REQUEST, successResponse);
        verify(userService, times(1)).updateUser(any());
    }

    @Test
    void updateUserTestBadRequest() throws Exception {
        ErrorResponse errorResponse = badRequestResponse("Идентификатор пользователя не может быть пустым");
        doPutTest(USER_BASE_URI, UPDATE_USER_BAD_REQUEST, errorResponse);
        verify(userService, times(0)).updateUser(any());
    }

    @Test
    void updateUserTestServerError() throws Exception {
        ErrorResponse errorResponse = serverErrorResponse("Сообщение об ошибке");
        when(userService.updateUser(any())).thenThrow(new RuntimeException("Сообщение об ошибке"));
        doPutTest(USER_BASE_URI, UPDATE_USER_REQUEST_FOR_EXCEPTION, errorResponse);
        verify(userService, times(1)).updateUser(any());
    }

    @Test
    void deleteUserTestSuccess() throws Exception {
        Response successResponse = successResponse(DELETE_USER_REQUEST.getRequestId().toString());
        when(userService.deleteUser(any())).thenReturn(successResponse);
        doDeleteTest(USER_BASE_URI, DELETE_USER_REQUEST, successResponse);
        verify(userService, times(1)).deleteUser(any());
    }

    @Test
    void deleteUserTestBadRequest() throws Exception {
        ErrorResponse errorResponse = badRequestResponse("Идентификатор пользователя не может быть пустым");
        doDeleteTest(USER_BASE_URI, DELETE_USER_BAD_REQUEST, errorResponse);
        verify(userService, times(0)).deleteUser(any());
    }

    @Test
    void deleteUserTestServerError() throws Exception {
        ErrorResponse errorResponse = serverErrorResponse("Сообщение об ошибке");
        when(userService.deleteUser(any())).thenThrow(new RuntimeException("Сообщение об ошибке"));
        doDeleteTest(USER_BASE_URI, DELETE_USER_REQUEST_FOR_EXCEPTION, errorResponse);
        verify(userService, times(1)).deleteUser(any());
    }

    @Test
    void searchUserTestSuccess() throws Exception {
        UserResponse successResponse = successUserResponse(SEARCH_USER_REQUEST.getRequestId().toString());
        when(userService.searchUser(any(), anyInt(), anyInt(), any(), any())).thenReturn(successResponse);
        doPostTest(SEARCH_USER_URI, SEARCH_USER_REQUEST, successResponse);
        verify(userService, times(1)).searchUser(any(), anyInt(), anyInt(), any(), any());
    }

    @Test
    void searchUserTestBadRequest() throws Exception {
        ErrorResponse errorResponse = badRequestResponse("В нике должно быть не больше 20 символов");
        doPostTest(SEARCH_USER_URI, SEARCH_USER_BAD_REQUEST, errorResponse);
        verify(userService, times(0)).searchUser(any(), anyInt(), anyInt(), any(), any());
    }

    @Test
    void searchUserTestServerError() throws Exception {
        ErrorResponse errorResponse = serverErrorResponse("Сообщение об ошибке");
        when(userService.searchUser(any(), anyInt(), anyInt(), any(), any())).thenThrow(new RuntimeException("Сообщение об ошибке"));
        doPostTest(SEARCH_USER_URI, SEARCH_USER_REQUEST_FOR_EXCEPTION, errorResponse);
        verify(userService, times(1)).searchUser(any(), anyInt(), anyInt(), any(), any());
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
                                .queryParam("pageNumber", "0")
                                .queryParam("pageSize", "10")
                                .queryParam("direction", "ASC")
                                .queryParam("sortField", "id")
                )
                .andExpect(
                        _ -> {
                            if (request.equals(CREATE_USER_REQUEST) ||
                                    request.equals(SEARCH_USER_REQUEST)) status().isOk();
                            if (request.equals(CREATE_USER_BAD_REQUEST) ||
                                    request.equals(SEARCH_USER_BAD_REQUEST)) status().isBadRequest();
                            if (request.equals(CREATE_USER_REQUEST_FOR_EXCEPTION) ||
                                    request.equals(SEARCH_USER_REQUEST_FOR_EXCEPTION))
                                status().isInternalServerError();
                        }
                )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));
    }

    private void doPutTest(
            String uri,
            Object request,
            Response expectedResponse
    ) throws Exception {

        mockMvc.perform(
                        put(uri)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                                .accept(MediaType.APPLICATION_JSON)
                                .characterEncoding("utf-8")
                )
                .andExpect(
                        _ -> {
                            if (request.equals(UPDATE_USER_REQUEST)) status().isOk();
                            if (request.equals(UPDATE_USER_BAD_REQUEST)) status().isBadRequest();
                            if (request.equals(UPDATE_USER_REQUEST_FOR_EXCEPTION))
                                status().isInternalServerError();
                        }
                )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));
    }

    private void doDeleteTest(
            String uri,
            Object request,
            Response expectedResponse
    ) throws Exception {

        mockMvc.perform(
                        delete(uri)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                                .accept(MediaType.APPLICATION_JSON)
                                .characterEncoding("utf-8")
                )
                .andExpect(
                        _ -> {
                            if (request.equals(DELETE_USER_REQUEST)) status().isOk();
                            if (request.equals(DELETE_USER_BAD_REQUEST)) status().isBadRequest();
                            if (request.equals(DELETE_USER_REQUEST_FOR_EXCEPTION))
                                status().isInternalServerError();
                        }
                )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));
    }
}
