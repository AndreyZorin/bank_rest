package com.example.bankcards;

import com.example.bankcards.constant.OperationStatus;
import com.example.bankcards.dto.response.ErrorResponse;
import com.example.bankcards.exception.ObjectNotFoundException;
import jakarta.security.auth.message.AuthException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * Тестовый обработчик ошибок контроллеров.
 */
@RestControllerAdvice
public class TestExceptionHandler {

    /**
     * Обработка RuntimeException.
     *
     * @param exception {@link RuntimeException}
     * @return {@link ResponseEntity<ErrorResponse>}
     */
    @org.springframework.web.bind.annotation.ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ErrorResponse> handleRuntimeException(
            @NonNull RuntimeException exception
    ) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        new ErrorResponse(
                                OperationStatus.ERROR,
                                "",
                                ErrorResponse.serverErrorMessage(exception.getMessage())
                        )
                );
    }

    /**
     * Обработка AuthException.
     *
     * @param exception {@link AuthException}
     * @return {@link ResponseEntity<ErrorResponse>}
     */
    @org.springframework.web.bind.annotation.ExceptionHandler({AuthException.class})
    public ResponseEntity<ErrorResponse> handleAuthException(
            @NonNull AuthException exception
    ) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(
                        new ErrorResponse(
                                OperationStatus.ERROR,
                                "",
                                ErrorResponse.authRequestMessage(exception.getMessage())
                        )
                );
    }

    /**
     * Обработка AuthorizationDeniedException.
     *
     * @param exception {@link AuthorizationDeniedException}
     * @return {@link ResponseEntity<ErrorResponse>}
     */
    @org.springframework.web.bind.annotation.ExceptionHandler({AuthorizationDeniedException.class})
    public ResponseEntity<ErrorResponse> handleAuthorizationDeniedException(
            @NonNull AuthorizationDeniedException exception
    ) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(
                        new ErrorResponse(
                                OperationStatus.ERROR,
                                "",
                                ErrorResponse.authRequestMessage(exception.getMessage())
                        )
                );
    }

    /**
     * Обработка ObjectNotFoundException.
     *
     * @param exception {@link ObjectNotFoundException}
     * @return {@link ResponseEntity<ErrorResponse>}
     */
    @org.springframework.web.bind.annotation.ExceptionHandler({ObjectNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleObjectNotFoundException(
            @NonNull ObjectNotFoundException exception
    ) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        new ErrorResponse(
                                OperationStatus.ERROR,
                                "",
                                exception.getMessage()
                        )
                );
    }

    /**
     * Обработка MethodArgumentNotValidException.
     *
     * @param exception {@link MethodArgumentNotValidException}
     * @return {@link ResponseEntity<ErrorResponse>}
     */
    @org.springframework.web.bind.annotation.ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
            @NonNull MethodArgumentNotValidException exception
    ) {
        String exceptionMsg = exception.getBindingResult().getAllErrors().stream()
                .map(error -> (error.getDefaultMessage() == null) ? "UNKNOWN_ERROR" : error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        new ErrorResponse(
                                OperationStatus.ERROR,
                                "",
                                ErrorResponse.badRequestMessage(exceptionMsg)
                        )
                );
    }
}
