package com.example.bankcards.dto.response;

import com.example.bankcards.constant.OperationStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

/**
 * Ответ с токенами доступа и обновления.
 */
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JwtResponse extends Response {

    /**
     * Тип токена.
     */
    private final String type = "Bearer";

    /**
     * Токен доступа.
     */
    private final String accessToken;

    /**
     * Токен обновления.
     */
    private final String refreshToken;

    public JwtResponse(OperationStatus operationStatus, String responseId, String accessToken, String refreshToken) {
        super(operationStatus, responseId);
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
