package com.example.bankcards;

import com.example.bankcards.constant.CardStatus;
import com.example.bankcards.constant.OperationStatus;
import com.example.bankcards.constant.RoleName;
import com.example.bankcards.dto.request.*;
import com.example.bankcards.dto.response.*;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class TestData {
    public static final String LOGIN_URI = "/api/v1/auth/login";
    public static final String ACCESS_TOKEN_URI = "/api/v1/auth/token";
    public static final String REFRESH_TOKEN_URI = "/api/v1/auth/refresh";
    public static final String TRANSFER_URI = "/api/v1/transactions/transfer";
    public static final String BALANCE_URI = "/api/v1/transactions/balance";
    public static final String CARD_BASE_URI = "/api/v1/cards";
    public static final String CARD_SEARCH_URI = "/api/v1/cards/search";
    public static final String CARD_BLOCK_REQUEST_URI = "/api/v1/cards/block-request";
    public static final String USER_BASE_URI = "/api/v1/users";
    public static final String SEARCH_USER_URI = "/api/v1/users/search";

    public static final String TEST_LOGIN = "test_user";
    public static final String TEST_PASSWORD = "test_password";
    public static final String TEST_ACCESS_TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0X3VzZXIiLCJyb2xlcyI6WyJVU0VSIl19.QQ3L36NbORuc-RKWm5KoPDoiSTFJ6J00kqsA9NuPMDIxn6UHn3wTZxMFG2G0FG1-U3VWVO7QRwfdCzUSy0UNaA";
    public static final String TEST_REFRESH_TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0X3VzZXIifQ.BDusIYSJqO5r1Xi7_hpuH4-du9l4lw4DeCrFcPpu9oLSKXv1GqMGLNqMqVzHNq07DhQtNttrMKmE44c31Efk5Q";

    public static JwtRequest LOGIN_REQUEST = new JwtRequest(
            UUID.randomUUID(),
            TEST_LOGIN,
            TEST_PASSWORD
    );

    public static JwtRequest LOGIN_REQUEST_FOR_EXCEPTION = new JwtRequest(
            UUID.randomUUID(),
            TEST_LOGIN,
            TEST_PASSWORD
    );

    public static JwtRequest LOGIN_BAD_REQUEST = new JwtRequest(
            UUID.randomUUID(),
            null,
            TEST_PASSWORD
    );

    public static RefreshJwtRequest GET_TOKEN_REQUEST = new RefreshJwtRequest(
            UUID.randomUUID(),
            TEST_REFRESH_TOKEN
    );

    public static RefreshJwtRequest GET_TOKEN_REQUEST_FOR_EXCEPTION = new RefreshJwtRequest(
            UUID.randomUUID(),
            TEST_REFRESH_TOKEN
    );

    public static RefreshJwtRequest GET_TOKEN_BAD_REQUEST = new RefreshJwtRequest(
            UUID.randomUUID(),
            null
    );

    public static TransactionRequest TRANSFER_REQUEST = new TransactionRequest(
            UUID.randomUUID(),
            "1212 1212 1212 1211",
            "1212 1212 1212 1212",
            BigDecimal.valueOf(10000)
    );

    public static TransactionRequest TRANSFER_REQUEST_FOR_EXCEPTION = new TransactionRequest(
            UUID.randomUUID(),
            "1212 1212 1212 1211",
            "1212 1212 1212 1212",
            BigDecimal.valueOf(1000)
    );

    public static TransactionRequest TRANSFER_BAD_REQUEST = new TransactionRequest(
            UUID.randomUUID(),
            "1212 1212 1212 1211",
            "1212 1212 1212 1212",
            null
    );

    public static TransactionRequest BALANCE_REQUEST = new TransactionRequest(
            UUID.randomUUID(),
            null,
            "1212 1212 1212 1212",
            null
    );

    public static TransactionRequest BALANCE_REQUEST_FOR_EXCEPTION = new TransactionRequest(
            UUID.randomUUID(),
            null,
            "1212 1212 1212 1212",
            null
    );

    public static TransactionRequest BALANCE_BAD_REQUEST = new TransactionRequest(
            UUID.randomUUID(),
            null,
            null,
            null
    );

    public static CardRequest CREATE_CARD_REQUEST = new CardRequest(
            UUID.randomUUID(),
            null,
            "1212 1212 1212 1212",
            null,
            null,
            123213213L,
            LocalDate.now().plusYears(1),
            CardStatus.ACTIVE,
            BigDecimal.valueOf(12000)

    );

    public static CardRequest CREATE_CARD_REQUEST_FOR_EXCEPTION = new CardRequest(
            UUID.randomUUID(),
            null,
            "1212 1212 1212 1212",
            null,
            null,
            123213213L,
            LocalDate.now().plusYears(1),
            CardStatus.ACTIVE,
            BigDecimal.valueOf(12000)

    );

    public static CardRequest CREATE_CARD_BAD_REQUEST = new CardRequest(
            UUID.randomUUID(),
            null,
            "null",
            null,
            null,
            123213213L,
            LocalDate.now().plusYears(1),
            CardStatus.ACTIVE,
            BigDecimal.valueOf(12000)

    );

    public static CardRequest UPDATE_CARD_REQUEST = new CardRequest(
            UUID.randomUUID(),
            null,
            "1212 1212 1212 1212",
            null,
            null,
            null,
            null,
            CardStatus.BLOCKED,
            null

    );

    public static CardRequest UPDATE_CARD_REQUEST_FOR_EXCEPTION = new CardRequest(
            UUID.randomUUID(),
            null,
            "1212 1212 1212 1212",
            null,
            null,
            null,
            null,
            CardStatus.BLOCKED,
            null

    );

    public static CardRequest UPDATE_CARD_BAD_REQUEST = new CardRequest(
            UUID.randomUUID(),
            null,
            null,
            null,
            null,
            null,
            null,
            CardStatus.BLOCKED,
            null

    );

    public static CardRequest DELETE_AND_BLOCK_CARD_REQUEST = new CardRequest(
            UUID.randomUUID(),
            null,
            "1212 1212 1212 1212",
            null,
            null,
            null,
            null,
            null,
            null

    );

    public static CardRequest DELETE_AND_BLOCK_CARD_REQUEST_FOR_EXCEPTION = new CardRequest(
            UUID.randomUUID(),
            null,
            "1212 1212 1212 1212",
            null,
            null,
            null,
            null,
            null,
            null

    );

    public static CardRequest DELETE_AND_BLOCK_CARD_BAD_REQUEST = new CardRequest(
            UUID.randomUUID(),
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null

    );

    public static CardRequest SEARCH_CARD_REQUEST = new CardRequest(
            UUID.randomUUID(),
            123123213L,
            "1212 1212 1212 1212",
            "username",
            "Joe Doe",
            null,
            null,
            CardStatus.ACTIVE,
            null

    );

    public static CardRequest SEARCH_CARD_REQUEST_FOR_EXCEPTION = new CardRequest(
            UUID.randomUUID(),
            123123213L,
            "1212 1212 1212 1212",
            "username",
            "Joe Doe",
            null,
            null,
            CardStatus.ACTIVE,
            null

    );

    public static CardRequest SEARCH_CARD_BAD_REQUEST = new CardRequest(
            UUID.randomUUID(),
            123123213L,
            "1212 1212 1212 1212",
            "usernameusernameusername",
            "Joe Doe",
            null,
            null,
            CardStatus.ACTIVE,
            null

    );

    public static UserRequest CREATE_USER_REQUEST = new UserRequest(
            UUID.randomUUID(),
            null,
            "username",
            "password",
            "Joe",
            "Doe",
            Set.of(
                    new RoleRequest(
                            RoleName.USER
                    )
            )

    );

    public static UserRequest CREATE_USER_REQUEST_FOR_EXCEPTION = new UserRequest(
            UUID.randomUUID(),
            null,
            "username",
            "password",
            "Joe",
            "Doe",
            Set.of(
                    new RoleRequest(
                            RoleName.USER
                    )
            )

    );

    public static UserRequest CREATE_USER_BAD_REQUEST = new UserRequest(
            UUID.randomUUID(),
            null,
            null,
            "password",
            "Joe",
            "Doe",
            Set.of(
                    new RoleRequest(
                            RoleName.USER
                    )
            )

    );

    public static UserRequest UPDATE_USER_REQUEST = new UserRequest(
            UUID.randomUUID(),
            1232L,
            "username",
            null,
            null,
            null,
            Set.of(
                    new RoleRequest(
                            RoleName.ADMIN
                    )
            )

    );

    public static UserRequest UPDATE_USER_REQUEST_FOR_EXCEPTION = new UserRequest(
            UUID.randomUUID(),
            1232L,
            "username",
            null,
            null,
            null,
            Set.of(
                    new RoleRequest(
                            RoleName.ADMIN
                    )
            )

    );

    public static UserRequest UPDATE_USER_BAD_REQUEST = new UserRequest(
            UUID.randomUUID(),
            null,
            null,
            "password",
            "Joe",
            "Doe",
            Set.of(
                    new RoleRequest(
                            RoleName.USER
                    )
            )

    );

    public static UserRequest DELETE_USER_REQUEST = new UserRequest(
            UUID.randomUUID(),
            1232L,
            null,
            null,
            null,
            null,
            null

    );

    public static UserRequest DELETE_USER_REQUEST_FOR_EXCEPTION = new UserRequest(
            UUID.randomUUID(),
            1232L,
            null,
            null,
            null,
            null,
            null

    );

    public static UserRequest DELETE_USER_BAD_REQUEST = new UserRequest(
            UUID.randomUUID(),
            null,
            null,
            null,
            null,
            null,
            null

    );

    public static UserRequest SEARCH_USER_REQUEST = new UserRequest(
            UUID.randomUUID(),
            123123L,
            "username",
            null,
            "Joe",
            "Doe",
            null

    );

    public static UserRequest SEARCH_USER_REQUEST_FOR_EXCEPTION = new UserRequest(
            UUID.randomUUID(),
            123123L,
            "username",
            null,
            "Joe",
            "Doe",
            null

    );

    public static UserRequest SEARCH_USER_BAD_REQUEST = new UserRequest(
            UUID.randomUUID(),
            123123L,
            "usernameusernameusername",
            null,
            "Joe",
            "Doe",
            null

    );

    public static JwtResponse successAuthResponse(String responseId) {
        return new JwtResponse(OperationStatus.COMPLETED, responseId, TEST_ACCESS_TOKEN, TEST_REFRESH_TOKEN);
    }

    public static TransactionResponse successTransactionResponse(String responseId) {
        return new TransactionResponse(
                OperationStatus.COMPLETED,
                responseId,
                "1212 1212 1212 1211",
                BigDecimal.valueOf(10000),
                "1212 1212 1212 1212",
                BigDecimal.valueOf(10000),
                BigDecimal.valueOf(1000)
        );
    }

    public static UserResponse successUserResponse(String responseId) {
        return new UserResponse(
                OperationStatus.COMPLETED,
                responseId,
                List.of(
                        new UserResponse.UserInfo(
                                2133132L,
                                "username",
                                "firstName",
                                "lastName",
                                Set.of(
                                        new RoleResponse(
                                                12321L,
                                                RoleName.USER
                                        )
                                )
                        )
                )
        );
    }

    public static CardResponse successCardResponse(String responseId) {
        return new CardResponse(
                OperationStatus.COMPLETED,
                responseId,
                List.of(
                        new CardResponse.CardInfo(
                                121212,
                                "1212 1212 1212 1212",
                                "username",
                                "firstName",
                                "lastName",
                                LocalDate.now().plusYears(1),
                                CardStatus.ACTIVE,
                                BigDecimal.valueOf(120000)
                        )
                )
        );
    }

    public static Response successResponse(String responseId) {
        return new Response(
                OperationStatus.COMPLETED,
                responseId
        );
    }

    public static ErrorResponse badRequestResponse(String exceptionMsg) {
        return new ErrorResponse(
                OperationStatus.ERROR,
                "",
                ErrorResponse.badRequestMessage(exceptionMsg)
        );
    }

    public static ErrorResponse serverErrorResponse(String exceptionMsg) {
        return new ErrorResponse(
                OperationStatus.ERROR,
                "",
                ErrorResponse.serverErrorMessage(exceptionMsg)
        );
    }

    public static Claims getTestRefreshClaims() {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64.decode("zL1HB3Pch05Avfynovxrf/kpF9O2m4NCWKJUjEp27s9J2jEG3ifiKCGylaZ8fDeoONSTJP/wAzKawB8F9rOMNg==")))
                .build()
                .parseClaimsJws(TEST_REFRESH_TOKEN)
                .getBody();
    }
}
