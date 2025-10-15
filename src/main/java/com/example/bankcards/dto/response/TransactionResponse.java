package com.example.bankcards.dto.response;

import com.example.bankcards.constant.OperationStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

import static com.example.bankcards.util.MaskingUtils.maskCardNumber;

/**
 * Ответ для операций с транзакциями.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionResponse extends Response {

    /**
     * Номер карты для списания.
     */
    String cardNumberSource;

    /**
     * Баланс карты для списания.
     */
    BigDecimal balanceSource;

    /**
     * Номер карты для зачисления.
     */
    String cardNumberTarget;

    /**
     * Баланс карты для зачисления.
     */
    BigDecimal balanceTarget;

    /**
     * Сумма транзакции.
     */
    BigDecimal sum;

    public TransactionResponse(
            OperationStatus operationStatus,
            String responseId,
            String cardNumberSource,
            BigDecimal balanceSource,
            String cardNumberTarget,
            BigDecimal balanceTarget,
            BigDecimal sum) {
        super(operationStatus, responseId);
        this.cardNumberSource = cardNumberSource;
        this.balanceSource = balanceSource;
        this.cardNumberTarget = cardNumberTarget;
        this.balanceTarget = balanceTarget;
        this.sum = sum;
    }

    public TransactionResponse(OperationStatus operationStatus, String responseId, String cardNumberTarget, BigDecimal balanceTarget) {
        super(operationStatus, responseId);
        this.cardNumberTarget = cardNumberTarget;
        this.balanceTarget = balanceTarget;
    }

    public String getCardNumberSource() {
        return maskCardNumber(cardNumberSource);
    }

    public String getCardNumberTarget() {
        return maskCardNumber(cardNumberTarget);
    }

    @Override
    public String toString() {
        return "TransactionResponse{" +
                "cardNumberSource='" + maskCardNumber(cardNumberSource) + '\'' +
                ", balanceSource=" + balanceSource +
                ", cardNumberTarget='" + maskCardNumber(cardNumberTarget) + '\'' +
                ", balanceTarget=" + balanceTarget +
                ", sum=" + sum +
                '}';
    }
}
