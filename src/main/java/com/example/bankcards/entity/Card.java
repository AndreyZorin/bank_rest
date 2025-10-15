package com.example.bankcards.entity;

import com.example.bankcards.constant.CardStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

/**
 * Сущность банковской карты.
 */
@Entity(name = "cards")
@Data
@NamedEntityGraph(
        name = "card-with-owner",
        attributeNodes = {
                @NamedAttributeNode(value = "owner"),
        }
)
public class Card {

    /**
     * Идентификатор карты.
     */
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Номер карты.
     */
    @Column(name = "card_number", unique = true, nullable = false)
    private String cardNumber;

    /**
     * Владелец карты.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User owner;

    /**
     * Срок действия карты.
     */
    @Column(name = "validity_period", nullable = false, columnDefinition = "date")
    private LocalDate validityPeriod;

    /**
     * Статус карты.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private CardStatus status;

    /**
     * Баланс карты.
     */
    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    /**
     * Запрос на блокировку карты от пользователя.
     */
    @Column(name = "block_request", nullable = false)
    private boolean blockRequest = false;

    /**
     * Время создания записи.
     */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    /**
     * Время обновления записи.
     */
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;
}
