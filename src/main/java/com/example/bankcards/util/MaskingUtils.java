package com.example.bankcards.util;

import org.springframework.lang.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Функции для маскирования данных.
 */
public class MaskingUtils {

    /**
     * Маскирование номера карты.
     *
     * @param cardNumber номер карты
     * @return маскированный номер карты
     */
    @Nullable
    public static String maskCardNumber(@Nullable String cardNumber) {
        if (cardNumber == null) {
            return null;
        }
        Pattern pattern = Pattern.compile("(\\d{4}\\s\\d{4}\\s\\d{4}\\s)(\\d{4})");
        Matcher matcher = pattern.matcher(cardNumber);
        if (matcher.find()) {
            cardNumber = matcher.group(1).replaceAll("\\d", "*") + matcher.group(2);
        }
        return cardNumber;
    }
}
