package org.example.entities;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * @brief Кастомная реализация интерфейса UserDetails для интеграции с Spring Security.
 * @details Используется для авторизации и аутентификации пользователей системы.
 */
public class CustomUserDetails implements UserDetails {

    /**
     * @brief Пользователь, связанный с деталями безопасности.
     */
    private User user;

    /**
     * @brief Конструктор с параметром.
     * @param user Пользователь, для которого создаются детали.
     */
    public CustomUserDetails(User user) {
        this.user = user;
    }

    /**
     * @brief Получить пользователя.
     * @return Объект пользователя.
     */
    public User getUser() {
        return user;
    }

    /**
     * @brief Установить пользователя.
     * @param user Объект пользователя.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @brief Получить список ролей пользователя.
     * @return Коллекция прав доступа пользователя.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().getRoleName().toUpperCase()));
    }

    /**
     * @brief Получить пароль пользователя.
     * @return Пароль пользователя.
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    ///** Считаем, что username - это email**/
    /**
     * @brief Получить имя пользователя (используется email).
     * @return Email пользователя.
     */
    @Override
    public String getUsername() {
        return user.getEmail();
    }

    /**
     * @brief Проверка, не истек ли срок действия аккаунта.
     * @return Всегда true (не истекает).
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * @brief Проверка, не заблокирован ли аккаунт.
     * @return Всегда true (не блокируется).
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * @brief Проверка, не истекли ли учетные данные.
     * @return Всегда true (не истекают).
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * @brief Проверка, включен ли аккаунт.
     * @return Всегда true (всегда включен).
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}