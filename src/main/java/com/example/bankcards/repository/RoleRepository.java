package com.example.bankcards.repository;

import com.example.bankcards.constant.RoleName;
import com.example.bankcards.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Получение множества ролей по множеству имён.
     *
     * @param names множество имён ролей
     * @return {@link Set<Role>}
     */
    @NonNull
    Set<Role> findAllByNameIn(@NonNull Set<RoleName> names);
}
