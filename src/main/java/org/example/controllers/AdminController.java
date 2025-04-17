package org.example.controllers;

import org.example.entities.User;
import org.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @class AdminController
 * @brief Контроллер для административных операций с пользователями.
 *
 * Обрабатывает запросы, связанные с управлением пользователями в административной панели.
 * Все методы доступны по пути /admin.
 * @author Елизавета Горновова
 * @version 1.0
 * @date 17.04.25
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    /**
     * @brief Конструктор с внедрением зависимости UserService.
     * @param userService Сервис для работы с пользователями
     */
    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    /**
     * @brief Отображает список всех пользователей.
     * @param model Модель для передачи данных в представление
     * @return Имя шаблона для отображения (admin_users.html)
     *
     * @details Метод:
     * 1. Получает список всех пользователей через userService
     * 2. Добавляет список пользователей в модель под атрибутом "users"
     * 3. Возвращает имя шаблона для отображения
     *
     * @note Доступен по GET /admin/users
     * @warning Требует прав администратора
     */
    @GetMapping("/users")
    public String adminUsers(Model model) {
        List<User> users = userService.getAllUsers();

        model.addAttribute("users", users);

        return "admin_users";
    }

    /**
     * @brief Блокирует/разблокирует пользователя.
     * @param id ID пользователя
     * @param blockStatus Флаг блокировки (true - заблокировать, false - разблокировать)
     * @return Перенаправление на страницу списка пользователей
     *
     * @details Метод:
     * 1. Обновляет статус блокировки пользователя через userService
     * 2. Перенаправляет на страницу /admin/users
     *
     * @note Доступен по POST /admin/users/{id}/block
     * @warning Требует прав администратора
     */
    @PostMapping("/users/{id}/block")
    public String blockUser(@PathVariable Long id, @RequestParam boolean blockStatus) {
        userService.updateBlockStatus(id, blockStatus);
        return "redirect:/admin/users";
    }

}