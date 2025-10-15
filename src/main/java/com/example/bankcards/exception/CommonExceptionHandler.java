package com.example.bankcards.exception;

import com.example.bankcards.constant.OperationStatus;
import com.example.bankcards.dto.response.ErrorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.security.auth.message.AuthException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.util.stream.Collectors;

/**
 * Обработчик ошибок контроллеров.
 */
@Slf4j
@RestControllerAdvice
public class CommonExceptionHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Обработка RuntimeException.
     *
     * @param exception {@link RuntimeException}
     * @param request   кэш запрос
     * @return {@link ResponseEntity<ErrorResponse>}
     */
    @org.springframework.web.bind.annotation.ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ErrorResponse> handleRuntimeException(
            @NonNull RuntimeException exception, @NonNull ContentCachingRequestWrapper request
    ) {
        String requestId = getRequestIdFromRequestContext(request);
        log.error("RequestId: {}. {}", requestId, exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        new ErrorResponse(
                                OperationStatus.ERROR,
                                requestId,
                                ErrorResponse.serverErrorMessage(exception.getMessage())
                        )
                );
    }

    /**
     * Обработка AuthException.
     *
     * @param exception {@link AuthException}
     * @param request   кэш запрос
     * @return {@link ResponseEntity<ErrorResponse>}
     */
    @org.springframework.web.bind.annotation.ExceptionHandler({AuthException.class})
    public ResponseEntity<ErrorResponse> handleAuthException(
            @NonNull AuthException exception, @NonNull ContentCachingRequestWrapper request
    ) {
        String requestId = getRequestIdFromRequestContext(request);
        log.error("RequestId: {}. {}", requestId, exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(
                        new ErrorResponse(
                                OperationStatus.ERROR,
                                requestId,
                                ErrorResponse.authRequestMessage(exception.getMessage())
                        )
                );
    }

    /**
     * Обработка AuthorizationDeniedException.
     *
     * @param exception {@link AuthorizationDeniedException}
     * @param request   кэш запрос
     * @return {@link ResponseEntity<ErrorResponse>}
     */
    @org.springframework.web.bind.annotation.ExceptionHandler({AuthorizationDeniedException.class})
    public ResponseEntity<ErrorResponse> handleAuthorizationDeniedException(
            @NonNull AuthorizationDeniedException exception, @NonNull ContentCachingRequestWrapper request
    ) {
        String requestId = getRequestIdFromRequestContext(request);
        log.error("RequestId: {}. {}", requestId, exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(
                        new ErrorResponse(
                                OperationStatus.ERROR,
                                requestId,
                                ErrorResponse.authRequestMessage(exception.getMessage())
                        )
                );
    }

    /**
     * Обработка ObjectNotFoundException.
     *
     * @param exception {@link ObjectNotFoundException}
     * @param request   кэш запрос
     * @return {@link ResponseEntity<ErrorResponse>}
     */
    @org.springframework.web.bind.annotation.ExceptionHandler({ObjectNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleObjectNotFoundException(
            @NonNull ObjectNotFoundException exception, @NonNull ContentCachingRequestWrapper request
    ) {
        String requestId = getRequestIdFromRequestContext(request);
        log.error("RequestId: {}. {}", requestId, exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        new ErrorResponse(
                                OperationStatus.ERROR,
                                requestId,
                                exception.getMessage()
                        )
                );
    }

    /**
     * Обработка MethodArgumentNotValidException.
     *
     * @param exception {@link MethodArgumentNotValidException}
     * @param request   кэш запрос
     * @return {@link ResponseEntity<ErrorResponse>}
     */
    @org.springframework.web.bind.annotation.ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
            @NonNull MethodArgumentNotValidException exception, @NonNull ContentCachingRequestWrapper request
    ) {
        String exceptionMsg = exception.getBindingResult().getAllErrors().stream()
                .map(error -> (error.getDefaultMessage() == null) ? "UNKNOWN_ERROR" : error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        String requestId = getRequestIdFromRequestContext(request);
        log.error("RequestId: {}. {}", requestId, exceptionMsg);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        new ErrorResponse(
                                OperationStatus.ERROR,
                                requestId,
                                ErrorResponse.badRequestMessage(exceptionMsg)
                        )
                );
    }

    /**
     * Получение идентификатора запроса из кэша запроса.
     *
     * @param request кэш запрос
     * @return {@link String} идентификатора запроса
     */
    @NonNull
    private String getRequestIdFromRequestContext(@NonNull ContentCachingRequestWrapper request) {
        String json = request.getContentAsString();
        JsonNode jsonNode;
        try {
            jsonNode = objectMapper.readTree(json);
        } catch (JsonProcessingException e) {
            jsonNode = null;
        }
        return (jsonNode != null && jsonNode.get("requestId") != null) ? jsonNode.get("requestId").asText() : "not defined";
    }
}
