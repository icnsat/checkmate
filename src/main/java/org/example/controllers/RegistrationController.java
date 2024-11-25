package org.example.controllers;

import org.example.entities.User;
import org.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class RegistrationController {
    private UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(UserService.class);

    @GetMapping("/registration")
    public String registration() {
        log.info("Registration page visited");
        return "registration";
    }

    /**с RequestParam**/
//    @PostMapping("/registration")
//    public String addUser(@RequestParam String email, String password) {//User user, Model model) {
//        log.info("Starting registration");
//        return userService.registerUser(email, password);//user, model);
//    }

    /**с ModelAttribute**/
    @PostMapping("/registration")
    public String addUser(@ModelAttribute User user, Model model) {
        log.info("Starting registration for user: {}", user.getEmail());
        return userService.registerUser(user, model);
    }

    @GetMapping("/login")
    public String login() {
        log.info("Login page visited");
        return "login";
    }

    /** Пыталась сделать редирект на страницу, с которой пользователя перебросило на логин,
     * а оказалосб, что это автоматически делается, если убрать из конфига defaultSuccessUrl **/
//    @PostMapping("/login")
//    public String loginUser(@RequestParam String username, @RequestParam String password, HttpSession session) {
//        boolean isAuthenticated = userService.authenticate(username, password);
//
//        if (isAuthenticated) {
//            session.setAttribute("user", userService.getUserByUsername(username));
//
//            // Проверяем, нужно ли перенаправить на сохраненный URL
//            String redirectUrl = (String) session.getAttribute("redirectAfterLogin");
//            if (redirectUrl != null) {
//                session.removeAttribute("redirectAfterLogin");
//                return "redirect:" + redirectUrl;
//            }
//
//            return "redirect:/"; // По умолчанию на главную страницу
//        }
//
//        return "login"; // Повторно показываем страницу логина
//    }

}