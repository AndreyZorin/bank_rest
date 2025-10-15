package com.example.bankcards.service;

import com.example.bankcards.constant.OperationStatus;
import com.example.bankcards.dto.filter.CardSearchFilter;
import com.example.bankcards.dto.request.CardRequest;
import com.example.bankcards.dto.response.CardResponse;
import com.example.bankcards.dto.response.Response;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.User;
import com.example.bankcards.exception.ObjectNotFoundException;
import com.example.bankcards.mapper.CardMapper;
import com.example.bankcards.service.impl.CardServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

import static com.example.bankcards.TestData.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CardServiceTests {

    @InjectMocks
    private CardServiceImpl subject;

    @Mock
    private DbService dbService;
    @Mock
    private CardMapper cardMapper;

    @Test
    void createCardSuccessTest() {
        Card card = mock(Card.class);
        User user = mock(User.class);
        CardResponse.CardInfo cardInfo =
                successCardResponse(CREATE_CARD_REQUEST.requestId().toString()).getCardInfoList().getFirst();

        when(cardMapper.toCard(any())).thenReturn(card);
        when(cardMapper.toCardInfo(any())).thenReturn(cardInfo);
        when(dbService.findUserById(any())).thenReturn(user);
        when(dbService.saveCard(any())).thenReturn(card);

        CardResponse cardResponse = subject.createCard(CREATE_CARD_REQUEST);
        assertEquals(OperationStatus.COMPLETED, cardResponse.getOperationStatus());
        assertEquals(cardResponse.getResponseId(), CREATE_CARD_REQUEST.getRequestId().toString());
        CardResponse.CardInfo responseCardInfo = cardResponse.getCardInfoList().getFirst();
        assertThat(cardInfo)
                .usingRecursiveComparison()
                .isEqualTo(responseCardInfo);

        verify(cardMapper, times(1)).toCard(any());
        verify(cardMapper, times(1)).toCardInfo(any());
        verify(dbService, times(1)).findUserById(any());
        verify(dbService, times(1)).saveCard(any());
    }

    @Test
    void createCardFailureNotFoundTest() {
        CardRequest request = mock(CardRequest.class);

        when(dbService.findUserById(any()))
                .thenThrow(new ObjectNotFoundException("Пользователь не найден"));

        String message = assertThrows(ObjectNotFoundException.class, () -> subject.createCard(request))
                .getMessage();
        assertEquals("Пользователь не найден", message);

        verify(cardMapper, times(1)).toCard(any());
        verify(cardMapper, times(0)).toCardInfo(any());
        verify(dbService, times(1)).findUserById(any());
        verify(dbService, times(0)).saveCard(any());
    }

    @Test
    void updateCardSuccessTest() {
        Card card = mock(Card.class);
        CardResponse.CardInfo cardInfo =
                successCardResponse(UPDATE_CARD_REQUEST.requestId().toString()).getCardInfoList().getFirst();

        when(dbService.findCardByCardNumberLockCard(any())).thenReturn(card);
        when(cardMapper.toCardInfo(any())).thenReturn(cardInfo);

        CardResponse cardResponse = subject.updateCard(UPDATE_CARD_REQUEST);
        assertEquals(OperationStatus.COMPLETED, cardResponse.getOperationStatus());
        assertEquals(cardResponse.getResponseId(), UPDATE_CARD_REQUEST.getRequestId().toString());
        CardResponse.CardInfo responseCardInfo = cardResponse.getCardInfoList().getFirst();
        assertThat(cardInfo)
                .usingRecursiveComparison()
                .isEqualTo(responseCardInfo);

        verify(dbService, times(1)).findCardByCardNumberLockCard(any());
        verify(cardMapper, times(1)).toCardInfo(any());
    }

    @Test
    void updateCardFailureNotFoundTest() {
        CardRequest request = mock(CardRequest.class);

        when(dbService.findCardByCardNumberLockCard(any()))
                .thenThrow(new ObjectNotFoundException("Карта не найдена"));

        String message = assertThrows(ObjectNotFoundException.class, () -> subject.updateCard(request))
                .getMessage();
        assertEquals("Карта не найдена", message);

        verify(dbService, times(1)).findCardByCardNumberLockCard(any());
        verify(cardMapper, times(0)).toCardInfo(any());
    }

    @Test
    void deleteCardSuccessTest() {
        when(dbService.existsCardByCardNumber(any())).thenReturn(false);

        Response cardResponse = subject.deleteCard(DELETE_AND_BLOCK_CARD_REQUEST);
        assertEquals(OperationStatus.COMPLETED, cardResponse.getOperationStatus());
        assertEquals(cardResponse.getResponseId(), DELETE_AND_BLOCK_CARD_REQUEST.getRequestId().toString());

        verify(dbService, times(1)).deleteCardByCardNumber(any());
        verify(dbService, times(1)).existsCardByCardNumber(any());
    }

    @Test
    void deleteCardFailureTest() {
        CardRequest request = mock(CardRequest.class);

        when(dbService.existsCardByCardNumber(any())).thenReturn(true);

        String message = assertThrows(IllegalStateException.class, () -> subject.deleteCard(request))
                .getMessage();
        assertEquals("Неудачная попытка удаления карты", message);

        verify(dbService, times(1)).deleteCardByCardNumber(any());
        verify(dbService, times(1)).existsCardByCardNumber(any());
    }

    @Test
    void searchCardSuccessTest() {
        CardSearchFilter filter = mock(CardSearchFilter.class);
        Page<Card> page = mock(Page.class);
        List<CardResponse.CardInfo> cardInfoList =
                successCardResponse(SEARCH_CARD_REQUEST.requestId().toString()).getCardInfoList();

        when(cardMapper.toCardSearchFilter(any())).thenReturn(filter);
        when(dbService.searchCard(any(), anyInt(), anyInt(), any(), any())).thenReturn(page);
        when(cardMapper.toCardInfoList(any())).thenReturn(cardInfoList);

        CardResponse cardResponse = subject.searchCard(SEARCH_CARD_REQUEST, 0, 10, Sort.Direction.ASC, "id");
        assertEquals(OperationStatus.COMPLETED, cardResponse.getOperationStatus());
        assertEquals(cardResponse.getResponseId(), SEARCH_CARD_REQUEST.getRequestId().toString());
        CardResponse.CardInfo responseCardInfo = cardResponse.getCardInfoList().getFirst();
        assertThat(cardInfoList.getFirst())
                .usingRecursiveComparison()
                .isEqualTo(responseCardInfo);

        verify(cardMapper, times(1)).toCardSearchFilter(any());
        verify(dbService, times(1)).searchCard(any(), anyInt(), anyInt(), any(), any());
        verify(cardMapper, times(1)).toCardInfoList(any());
    }

    @Test
    void blockRequestCardSuccessTest() {
        Card card = new Card();

        when(dbService.findCardByCardNumberLockCard(any())).thenReturn(card);

        Response cardResponse = subject.blockRequestCard(DELETE_AND_BLOCK_CARD_REQUEST);

        assertEquals(OperationStatus.COMPLETED, cardResponse.getOperationStatus());
        assertEquals(cardResponse.getResponseId(), DELETE_AND_BLOCK_CARD_REQUEST.getRequestId().toString());
        assertTrue(card.isBlockRequest());

        verify(dbService, times(1)).findCardByCardNumberLockCard(any());
    }

    @Test
    void blockRequestCardFailureNotFoundTest() {
        CardRequest request = mock(CardRequest.class);

        when(dbService.findCardByCardNumberLockCard(any()))
                .thenThrow(new ObjectNotFoundException("Карта не найдена"));

        String message = assertThrows(ObjectNotFoundException.class, () -> subject.blockRequestCard(request))
                .getMessage();
        assertEquals("Карта не найдена", message);

        verify(dbService, times(1)).findCardByCardNumberLockCard(any());
    }
}
