package org.example.repositories;

import org.example.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @brief Репозиторий для управления сущностями Role (Роли пользователей).
 * @details Содержит методы для работы с ролью пользователя, включая поиск роли по имени.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * @brief Найти роль по имени.
     * @param roleName Имя роли.
     * @return Роль, найденная по имени, обёрнутая в Optional.
     */
    Optional<Role> findByRoleName(String roleName);
}