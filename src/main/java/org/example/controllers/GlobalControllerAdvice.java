package org.example.controllers;

import org.example.entities.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * @class GlobalControllerAdvice
 * @brief Глобальный контроллер для добавления общих атрибутов модели во все контроллеры.
 *
 * Используется для внедрения роли пользователя (userRole) во все модели контроллеров,
 * чтобы можно было использовать её в шаблонах представлений.
 * @author Елизавета Горновова
 * @version 1.0
 * @date 17.04.25
 */
@ControllerAdvice
public class GlobalControllerAdvice {

    /**
     * @brief Добавляет роль текущего пользователя в модель.
     *
     * Метод вызывается автоматически перед выполнением каждого контроллера и добавляет
     * атрибут "userRole" в модель, основываясь на данных аутентификации.
     *
     * @param authentication Объект аутентификации, содержащий данные о текущем пользователе
     * @return строка, представляющая роль пользователя (например, "ADMIN", "GUEST")
     */
    @ModelAttribute("userRole")
    public String userRole(Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            return userDetails.getUser().getRole().getRoleName();
        }
        return "GUEST"; // может поставить CUSTOMER?
    }
}