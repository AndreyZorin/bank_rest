package com.example.bankcards.controller;

import com.example.bankcards.dto.response.ErrorResponse;
import com.example.bankcards.dto.response.Response;
import com.example.bankcards.dto.response.TransactionResponse;
import com.example.bankcards.security.service.JwtProvider;
import com.example.bankcards.service.TransactionService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TransactionController.class)
@AutoConfigureMockMvc(addFilters = false)
public class TransactionControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TransactionService transactionService;

    @MockitoBean
    private JwtProvider jwtProvider;

    @Test
    void cardToCardTransferTestSuccess() throws Exception {
        TransactionResponse successResponse = successTransactionResponse(TRANSFER_REQUEST.getRequestId().toString());
        when(transactionService.cardToCardTransfer(any())).thenReturn(successResponse);
        doPostTest(TRANSFER_URI, TRANSFER_REQUEST, successResponse);
        verify(transactionService, times(1)).cardToCardTransfer(any());
    }

    @Test
    void cardToCardTransferTestBadRequest() throws Exception {
        ErrorResponse errorResponse = badRequestResponse("Сумма перевода не может быть пустой");
        doPostTest(TRANSFER_URI, TRANSFER_BAD_REQUEST, errorResponse);
        verify(transactionService, times(0)).cardToCardTransfer(any());
    }

    @Test
    void cardToCardTransferTestServerError() throws Exception {
        ErrorResponse errorResponse = serverErrorResponse("Сообщение об ошибке");
        when(transactionService.cardToCardTransfer(any())).thenThrow(new RuntimeException("Сообщение об ошибке"));
        doPostTest(TRANSFER_URI, TRANSFER_REQUEST_FOR_EXCEPTION, errorResponse);
        verify(transactionService, times(1)).cardToCardTransfer(any());
    }

    @Test
    void getNewAccessTokenTestSuccess() throws Exception {
        TransactionResponse successResponse = successTransactionResponse(BALANCE_REQUEST.getRequestId().toString());
        when(transactionService.cardBalance(any())).thenReturn(successResponse);
        doPostTest(BALANCE_URI, BALANCE_REQUEST, successResponse);
        verify(transactionService, times(1)).cardBalance(any());
    }

    @Test
    void getNewAccessTokenTestBadRequest() throws Exception {
        ErrorResponse errorResponse = badRequestResponse("Номер карты не может быть пустым");
        doPostTest(BALANCE_URI, BALANCE_BAD_REQUEST, errorResponse);
        verify(transactionService, times(0)).cardBalance(any());
    }

    @Test
    void getNewAccessTokenTestServerError() throws Exception {
        ErrorResponse errorResponse = serverErrorResponse("Сообщение об ошибке");
        when(transactionService.cardBalance(any())).thenThrow(new RuntimeException("Сообщение об ошибке"));
        doPostTest(BALANCE_URI, BALANCE_REQUEST_FOR_EXCEPTION, errorResponse);
        verify(transactionService, times(1)).cardBalance(any());
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
                            if (request.equals(TRANSFER_REQUEST) ||
                                    request.equals(BALANCE_REQUEST)) status().isOk();
                            if (request.equals(TRANSFER_BAD_REQUEST) ||
                                    request.equals(BALANCE_BAD_REQUEST)) status().isBadRequest();
                            if (request.equals(TRANSFER_REQUEST_FOR_EXCEPTION) ||
                                    request.equals(BALANCE_REQUEST_FOR_EXCEPTION))
                                status().isInternalServerError();
                        }
                )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));
    }
}
