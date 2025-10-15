package com.example.bankcards.util;

import lombok.extern.slf4j.Slf4j;

/**
 * Функции для логирования.
 */
@Slf4j
public class LogUtils {

    /**
     * Логирование на уровнях INFO и DEBUG.
     *
     * @param mainMessage основное сообщение.
     * @param payload дополнительные данные
     */
    public static void logByLevelWithPayload(String mainMessage, Object payload) {

        if (log.isDebugEnabled()) {
            log.debug("{}, Payload: {}", mainMessage, payload);
        } else {
            log.info(mainMessage);
        }
    }
}
