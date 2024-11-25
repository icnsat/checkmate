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

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

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


    /**с ModelAttribute**/
    public String registerUser(User user) {
        // Проверяем, существует ли пользователь с таким email
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return "Email уже используется!";
        }

        // Находим роль "CUSTOMER" в базе данных
        Role userRole = roleRepository.findByRoleName("CUSTOMER")
                .orElseThrow(() -> new IllegalStateException("Role 'CUSTOMER' was not found in database >:("));

        // Кодируем пароль
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Присваиваем роль и сохраняем пользователя
        user.setRole(userRole);
        userRepository.save(user);

        return "redirect:/login";
    }


    public User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        return customUserDetails.getUser();
    }


}
