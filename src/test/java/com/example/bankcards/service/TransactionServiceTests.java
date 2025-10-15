package com.example.bankcards.service;

import com.example.bankcards.dto.request.TransactionRequest;
import com.example.bankcards.dto.response.TransactionResponse;
import com.example.bankcards.entity.Card;
import com.example.bankcards.exception.ObjectNotFoundException;
import com.example.bankcards.service.impl.TransactionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static com.example.bankcards.TestData.TRANSFER_REQUEST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTests {

    @InjectMocks
    private TransactionServiceImpl subject;

    @Mock
    private DbService dbService;

    @Test
    void cardToCardTransferSuccessTest() {
        Card cardSource = new Card();
        cardSource.setBalance(BigDecimal.valueOf(120000));
        cardSource.setCardNumber(TRANSFER_REQUEST.cardNumberSource());
        Card cardTarget = new Card();
        cardTarget.setBalance(BigDecimal.valueOf(120000));
        cardTarget.setCardNumber(TRANSFER_REQUEST.cardNumberTarget());

        when(dbService.findCardByCardNumberLockCard(TRANSFER_REQUEST.cardNumberSource())).thenReturn(cardSource);
        when(dbService.findCardByCardNumberLockCard(TRANSFER_REQUEST.cardNumberTarget())).thenReturn(cardTarget);

        TransactionResponse transactionResponse = subject.cardToCardTransfer(TRANSFER_REQUEST);
        assertEquals(transactionResponse.getCardNumberSource().substring(15), TRANSFER_REQUEST.cardNumberSource().substring(15));
        assertEquals(transactionResponse.getCardNumberTarget().substring(15), TRANSFER_REQUEST.cardNumberTarget().substring(15));
        assertEquals(transactionResponse.getBalanceSource(), cardSource.getBalance());
        assertEquals(transactionResponse.getBalanceTarget(), cardTarget.getBalance());
        assertEquals(transactionResponse.getSum(), TRANSFER_REQUEST.sum());

        verify(dbService, times(2)).findCardByCardNumberLockCard(any());
    }

    @Test
    void cardToCardTransferFailureInsufficientFundsTest() {
        Card cardSource = new Card();
        cardSource.setBalance(BigDecimal.valueOf(9000));

        when(dbService.findCardByCardNumberLockCard(TRANSFER_REQUEST.cardNumberSource())).thenReturn(cardSource);

        String message = assertThrows(IllegalStateException.class, () -> subject.cardToCardTransfer(TRANSFER_REQUEST))
                .getMessage();
        assertEquals("Недостаточно средств", message);

        verify(dbService, times(1)).findCardByCardNumberLockCard(any());
    }

    @Test
    void cardToCardTransferFailureNotFoundTest() {
        TransactionRequest request = mock(TransactionRequest.class);

        when(dbService.findCardByCardNumberLockCard(request.cardNumberSource()))
                .thenThrow(new ObjectNotFoundException("Карта не найдена"));

        String message = assertThrows(ObjectNotFoundException.class, () -> subject.cardToCardTransfer(request))
                .getMessage();
        assertEquals("Карта не найдена", message);

        verify(dbService, times(1)).findCardByCardNumberLockCard(any());
    }

    @Test
    void cardBalanceSuccessTest() {
        Card cardTarget = new Card();
        cardTarget.setBalance(BigDecimal.valueOf(120000));
        cardTarget.setCardNumber(TRANSFER_REQUEST.cardNumberTarget());

        when(dbService.findCardByCardNumber(TRANSFER_REQUEST.cardNumberTarget())).thenReturn(cardTarget);

        TransactionResponse transactionResponse = subject.cardBalance(TRANSFER_REQUEST);
        assertEquals(transactionResponse.getCardNumberTarget().substring(15), TRANSFER_REQUEST.cardNumberTarget().substring(15));
        assertEquals(transactionResponse.getBalanceTarget(), cardTarget.getBalance());

        verify(dbService, times(1)).findCardByCardNumber(any());
    }

    @Test
    void cardBalanceFailureNotFoundTest() {
        TransactionRequest request = mock(TransactionRequest.class);

        when(dbService.findCardByCardNumber(request.cardNumberSource()))
                .thenThrow(new ObjectNotFoundException("Карта не найдена"));

        String message = assertThrows(ObjectNotFoundException.class, () -> subject.cardBalance(request))
                .getMessage();
        assertEquals("Карта не найдена", message);

        verify(dbService, times(1)).findCardByCardNumber(any());
    }
}
