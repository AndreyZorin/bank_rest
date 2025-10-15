package com.example.bankcards.dto.response;

import com.example.bankcards.constant.CardStatus;
import com.example.bankcards.constant.OperationStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static com.example.bankcards.util.MaskingUtils.maskCardNumber;

/**
 * Ответ для операций с картами.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CardResponse extends Response {

    /**
     * Список данных карты.
     */
    private List<CardInfo> cardInfoList;

    public CardResponse(OperationStatus operationStatus, String responseId, List<CardInfo> cardInfoList) {
        super(operationStatus, responseId);
        this.cardInfoList = cardInfoList;
    }

    /**
     * Данные карты.
     */
    @Data
    @AllArgsConstructor
    public static class CardInfo {
        private long cardId;
        private String cardNumber;
        private String ownerUsername;
        private String ownerFirstName;
        private String ownerLastName;
        private LocalDate validityPeriod;
        private CardStatus status;
        private BigDecimal balance;

        public String getCardNumber() {
            return maskCardNumber(cardNumber);
        }

        @Override
        public String toString() {
            return "CardInfo{" +
                    "cardId=" + cardId +
                    ", cardNumber='" + maskCardNumber(cardNumber) + '\'' +
                    ", ownerUsername='" + ownerUsername + '\'' +
                    ", ownerFirstName='" + ownerFirstName + '\'' +
                    ", ownerLastName='" + ownerLastName + '\'' +
                    ", validityPeriod=" + validityPeriod +
                    ", status=" + status +
                    ", balance=" + balance +
                    '}';
        }
    }
}
