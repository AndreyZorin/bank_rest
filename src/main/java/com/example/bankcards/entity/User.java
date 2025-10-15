package com.example.bankcards.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Set;

/**
 * Сущность пользователя(владельца карты).
 */
@Entity(name = "users")
@Data
@NamedEntityGraph(
        name = "user-with-roles",
        attributeNodes = {
                @NamedAttributeNode(value = "roles"),
        }
)
public class User implements UserDetails {

    /**
     * Идентификатор пользователя.
     */
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Ник пользователя.
     */
    @Column(name = "username", unique = true, nullable = false)
    private String username;

    /**
     * Пароль пользователя.
     */
    @Column(name = "password", unique = true, nullable = false)
    private String password;

    /**
     * Имя пользователя.
     */
    @Column(name = "first_name", nullable = false)
    private String firstName;

    /**
     * Фамилия пользователя.
     */
    @Column(name = "last_name", nullable = false)
    private String lastName;

    /**
     * Роли пользователя.
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }
}
