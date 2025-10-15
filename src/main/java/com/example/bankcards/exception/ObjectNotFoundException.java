package com.example.bankcards.exception;

import jakarta.annotation.Nonnull;

/**
 * Ошибка, возникающая при отсутствии искомого объекта.
 */
public class ObjectNotFoundException extends NullPointerException {

    /**
     * Конструктор класса.
     *
     * @param message сообщение об ошибке
     */
    public ObjectNotFoundException(@Nonnull String message) {
        super(message);
    }

}
