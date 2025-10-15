package com.example.bankcards.dto.response;

import com.example.bankcards.constant.OperationStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Ответ об ошибке.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse extends Response {

    /**
     * Сообщение об ошибке.
     */
    private String errorMessage;

    public ErrorResponse(OperationStatus operationStatus, String responseId, String errorMessage) {
        super(operationStatus, responseId);
        this.errorMessage = errorMessage;
    }

    public static String serverErrorMessage(String exceptionMsg) {
        return (exceptionMsg == null) ? "Внутренняя ошибка сервиса" : String.format("Внутренняя ошибка сервиса: %s", exceptionMsg);
    }

    public static String badRequestMessage(String exceptionMsg) {
        return (exceptionMsg == null) ? "Нарушение формата данных" : String.format("Нарушение формата данных: %s", exceptionMsg);
    }

    public static String authRequestMessage(String exceptionMsg) {
        return (exceptionMsg == null) ? "Ошибка авторизации" : String.format("Ошибка авторизации: %s", exceptionMsg);
    }
}
