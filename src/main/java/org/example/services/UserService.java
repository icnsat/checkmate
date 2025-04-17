package org.example.services;

import org.example.entities.CustomUserDetails;
import org.example.entities.Role;
import org.example.entities.User;
import org.example.repositories.RoleRepository;
import org.example.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

/**
 * @brief Сервис для управления пользователями.
 * @details Предоставляет функциональность регистрации пользователей, получения текущего пользователя,
 * управления блокировкой пользователей и работы с ролями.
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * @brief Конструктор UserService.
     * @param userRepository Репозиторий пользователей.
     * @param roleRepository Репозиторий ролей.
     * @param passwordEncoder Кодировщик паролей.
     */
    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**с RequestParam**/
//    public String registerUser(String email, String password) {
//        // Проверяем, существует ли пользователь с таким email
//        if (userRepository.findByEmail(email).isPresent()) {
//            return "Email уже используется!";
//        }
//
//        // Находим роль "CUSTOMER" в базе данных
//        Role userRole = roleRepository.findByRoleName("CUSTOMER")
//                .orElseThrow(() -> new IllegalStateException("Role 'CUSTOMER' was not found in data base >:("));
//
//        // Создаем нового пользователя
//        User user = new User();
//        user.setEmail(email);
//        user.setPassword(passwordEncoder.encode(password));
//        user.setRole(userRole);
//
//        // Сохраняем пользователя в базу данных
//        userRepository.save(user);
//
//        return "redirect:/login";
//    }


    // /**с ModelAttribute**/
    /**
     * @brief Регистрация нового пользователя через форму (ModelAttribute).
     * @details Проверяет, существует ли уже пользователь с таким email. Если нет — создаёт нового
     * пользователя с ролью "CUSTOMER", кодирует пароль и сохраняет в базу данных.
     *
     * @param user Объект пользователя, полученный из формы.
     * @param model Модель для передачи ошибок в представление.
     * @return Строка-перенаправление на страницу входа или регистрации.
     */
    public String registerUser(User user, Model model) {
        // Проверяем, существует ли пользователь с таким email
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            model.addAttribute("emailError", "Уже есть аккаунт для введённого адреса электронной почты!");
            return "redirect:/registration";
        }

        // Находим роль "CUSTOMER" в базе данных
        Role userRole = roleRepository.findByRoleName("CUSTOMER")
                .orElseThrow(() -> new IllegalStateException("Role 'CUSTOMER' was not found in database >:("));

        // Кодируем пароль
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Присваиваем роль и статус блокирования и сохраняем пользователя
        user.setRole(userRole);
        user.setIsBlocked(false);
        userRepository.save(user);

        return "redirect:/login";
    }

    /**
     * @brief Получение текущего авторизованного пользователя.
     * @return Объект текущего пользователя.
     */
    public User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        return customUserDetails.getUser();
    }

    /**
     * @brief Получение всех пользователей.
     * @return Список всех пользователей в системе.
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * @brief Обновление статуса блокировки пользователя.
     * @details Позволяет администратору заблокировать или разблокировать пользователя.
     *
     * @param userId Идентификатор пользователя.
     * @param blockStatus Новый статус блокировки (true — заблокирован).
     */
    public void updateBlockStatus(Long userId, boolean blockStatus) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        user.setIsBlocked(blockStatus);
        userRepository.save(user);
    }

}