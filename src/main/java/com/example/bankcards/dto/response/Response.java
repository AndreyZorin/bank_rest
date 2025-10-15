package com.example.bankcards.dto.response;

import com.example.bankcards.constant.OperationStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Базовое ДТО ответа сервиса.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

    /**
     * Статус выполненной операции.
     */
    private OperationStatus operationStatus;

    /**
     * Идентификатор ответа, соответствует идентификатору запроса.
     */
    private String responseId;
}
