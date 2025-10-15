package com.example.bankcards.dto.response;

import com.example.bankcards.constant.OperationStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Set;

/**
 * Ответ для операций с пользователями.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse extends Response {

    /**
     * Список данных пользователя.
     */
    private List<UserInfo> userInfoList;

    public UserResponse(OperationStatus operationStatus, String responseId, List<UserInfo> userInfoList) {
        super(operationStatus, responseId);
        this.userInfoList = userInfoList;
    }

    /**
     * Данные пользователя.
     */
    @Data
    @AllArgsConstructor
    public static class UserInfo {
        private long userId;
        private String username;
        private String firstName;
        private String lastName;
        private Set<RoleResponse> roles;
    }
}
