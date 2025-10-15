package com.example.bankcards.controller;

import com.example.bankcards.dto.response.CardResponse;
import com.example.bankcards.dto.response.ErrorResponse;
import com.example.bankcards.dto.response.Response;
import com.example.bankcards.security.service.JwtProvider;
import com.example.bankcards.service.CardService;
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

@WebMvcTest(controllers = CardController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CardControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CardService cardService;

    @MockitoBean
    private JwtProvider jwtProvider;

    @Test
    void createCardTestSuccess() throws Exception {
        CardResponse successResponse = successCardResponse(CREATE_CARD_REQUEST.getRequestId().toString());
        when(cardService.createCard(any())).thenReturn(successResponse);
        doPostTest(CARD_BASE_URI, CREATE_CARD_REQUEST, successResponse);
        verify(cardService, times(1)).createCard(any());
    }

    @Test
    void createCardTestBadRequest() throws Exception {
        ErrorResponse errorResponse = badRequestResponse("Номер карты не соответствует паттерну");
        doPostTest(CARD_BASE_URI, CREATE_CARD_BAD_REQUEST, errorResponse);
        verify(cardService, times(0)).createCard(any());
    }

    @Test
    void createCardTestServerError() throws Exception {
        ErrorResponse errorResponse = serverErrorResponse("Сообщение об ошибке");
        when(cardService.createCard(any())).thenThrow(new RuntimeException("Сообщение об ошибке"));
        doPostTest(CARD_BASE_URI, CREATE_CARD_REQUEST_FOR_EXCEPTION, errorResponse);
        verify(cardService, times(1)).createCard(any());
    }

    @Test
    void updateCardTestSuccess() throws Exception {
        CardResponse successResponse = successCardResponse(UPDATE_CARD_REQUEST.getRequestId().toString());
        when(cardService.updateCard(any())).thenReturn(successResponse);
        doPatchTest(CARD_BASE_URI, UPDATE_CARD_REQUEST, successResponse);
        verify(cardService, times(1)).updateCard(any());
    }

    @Test
    void updateCardTestBadRequest() throws Exception {
        ErrorResponse errorResponse = badRequestResponse("Номер карты не может быть пустым");
        doPatchTest(CARD_BASE_URI, UPDATE_CARD_BAD_REQUEST, errorResponse);
        verify(cardService, times(0)).updateCard(any());
    }

    @Test
    void updateCardTestServerError() throws Exception {
        ErrorResponse errorResponse = serverErrorResponse("Сообщение об ошибке");
        when(cardService.updateCard(any())).thenThrow(new RuntimeException("Сообщение об ошибке"));
        doPatchTest(CARD_BASE_URI, UPDATE_CARD_REQUEST_FOR_EXCEPTION, errorResponse);
        verify(cardService, times(1)).updateCard(any());
    }

    @Test
    void deleteCardTestSuccess() throws Exception {
        Response successResponse = successResponse(DELETE_AND_BLOCK_CARD_REQUEST.getRequestId().toString());
        when(cardService.deleteCard(any())).thenReturn(successResponse);
        doDeleteTest(CARD_BASE_URI, DELETE_AND_BLOCK_CARD_REQUEST, successResponse);
        verify(cardService, times(1)).deleteCard(any());
    }

    @Test
    void deleteCardTestBadRequest() throws Exception {
        ErrorResponse errorResponse = badRequestResponse("Номер карты не может быть пустым");
        doDeleteTest(CARD_BASE_URI, DELETE_AND_BLOCK_CARD_BAD_REQUEST, errorResponse);
        verify(cardService, times(0)).deleteCard(any());
    }

    @Test
    void deleteCardTestServerError() throws Exception {
        ErrorResponse errorResponse = serverErrorResponse("Сообщение об ошибке");
        when(cardService.deleteCard(any())).thenThrow(new RuntimeException("Сообщение об ошибке"));
        doDeleteTest(CARD_BASE_URI, DELETE_AND_BLOCK_CARD_REQUEST_FOR_EXCEPTION, errorResponse);
        verify(cardService, times(1)).deleteCard(any());
    }

    @Test
    void searchCardTestSuccess() throws Exception {
        CardResponse successResponse = successCardResponse(SEARCH_CARD_REQUEST.getRequestId().toString());
        when(cardService.searchCard(any(), anyInt(), anyInt(), any(), any())).thenReturn(successResponse);
        doPostTest(CARD_SEARCH_URI, SEARCH_CARD_REQUEST, successResponse);
        verify(cardService, times(1)).searchCard(any(), anyInt(), anyInt(), any(), any());
    }

    @Test
    void searchCardTestBadRequest() throws Exception {
        ErrorResponse errorResponse = badRequestResponse("В нике должно быть не больше 20 символов");
        doPostTest(CARD_SEARCH_URI, SEARCH_CARD_BAD_REQUEST, errorResponse);
        verify(cardService, times(0)).searchCard(any(), anyInt(), anyInt(), any(), any());
    }

    @Test
    void searchCardTestServerError() throws Exception {
        ErrorResponse errorResponse = serverErrorResponse("Сообщение об ошибке");
        when(cardService.searchCard(any(), anyInt(), anyInt(), any(), any())).thenThrow(new RuntimeException("Сообщение об ошибке"));
        doPostTest(CARD_SEARCH_URI, SEARCH_CARD_REQUEST_FOR_EXCEPTION, errorResponse);
        verify(cardService, times(1)).searchCard(any(), anyInt(), anyInt(), any(), any());
    }

    @Test
    void blockRequestCardTestSuccess() throws Exception {
        Response successResponse = successResponse(DELETE_AND_BLOCK_CARD_REQUEST.getRequestId().toString());
        when(cardService.blockRequestCard(any())).thenReturn(successResponse);
        doPostTest(CARD_BLOCK_REQUEST_URI, DELETE_AND_BLOCK_CARD_REQUEST, successResponse);
        verify(cardService, times(1)).blockRequestCard(any());
    }

    @Test
    void blockRequestCardTestBadRequest() throws Exception {
        ErrorResponse errorResponse = badRequestResponse("Номер карты не может быть пустым");
        doPostTest(CARD_BLOCK_REQUEST_URI, DELETE_AND_BLOCK_CARD_BAD_REQUEST, errorResponse);
        verify(cardService, times(0)).blockRequestCard(any());
    }

    @Test
    void blockRequestCardTestServerError() throws Exception {
        ErrorResponse errorResponse = serverErrorResponse("Сообщение об ошибке");
        when(cardService.blockRequestCard(any())).thenThrow(new RuntimeException("Сообщение об ошибке"));
        doPostTest(CARD_BLOCK_REQUEST_URI, DELETE_AND_BLOCK_CARD_REQUEST_FOR_EXCEPTION, errorResponse);
        verify(cardService, times(1)).blockRequestCard(any());
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
                            if (request.equals(CREATE_CARD_REQUEST) ||
                            request.equals(SEARCH_CARD_REQUEST) ||
                            request.equals(DELETE_AND_BLOCK_CARD_REQUEST)) status().isOk();
                            if (request.equals(CREATE_CARD_BAD_REQUEST) ||
                            request.equals(SEARCH_CARD_BAD_REQUEST) ||
                            request.equals(DELETE_AND_BLOCK_CARD_BAD_REQUEST)) status().isBadRequest();
                            if (request.equals(CREATE_CARD_REQUEST_FOR_EXCEPTION) ||
                            request.equals(SEARCH_CARD_REQUEST_FOR_EXCEPTION) ||
                            request.equals(DELETE_AND_BLOCK_CARD_REQUEST_FOR_EXCEPTION))
                                status().isInternalServerError();
                        }
                )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));
    }

    private void doPatchTest(
            String uri,
            Object request,
            Response expectedResponse
    ) throws Exception {

        mockMvc.perform(
                        patch(uri)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                                .accept(MediaType.APPLICATION_JSON)
                                .characterEncoding("utf-8")
                )
                .andExpect(
                        _ -> {
                            if (request.equals(UPDATE_CARD_REQUEST)) status().isOk();
                            if (request.equals(UPDATE_CARD_BAD_REQUEST)) status().isBadRequest();
                            if (request.equals(UPDATE_CARD_REQUEST_FOR_EXCEPTION))
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
                            if (request.equals(DELETE_AND_BLOCK_CARD_REQUEST)) status().isOk();
                            if (request.equals(DELETE_AND_BLOCK_CARD_BAD_REQUEST)) status().isBadRequest();
                            if (request.equals(DELETE_AND_BLOCK_CARD_REQUEST_FOR_EXCEPTION))
                                status().isInternalServerError();
                        }
                )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));
    }
}
