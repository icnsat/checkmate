package org.example;

import org.example.services.CustomUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @class SecurityConfig
 * @brief Основной класс конфигурации безопасности Spring Security.
 *
 * Настраивает аутентификацию, авторизацию и правила доступа для приложения.
 * @author Елизавета Горновова
 * @version 1.0
 * @date 17.04.25
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig{

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    /**
     * @brief Создает bean для кодирования паролей.
     * @return BCryptPasswordEncoder - реализация PasswordEncoder, использующая алгоритм BCrypt.
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * @brief Создает bean для загрузки данных пользователя.
     * @return UserDetailsService - кастомная реализация сервиса для работы с пользователями.
     */
    @Bean
    public UserDetailsService userDetailsService()
    {
        return new CustomUserDetailsService();
    }

    /**
     * @brief Настраивает провайдер аутентификации.
     * @return AuthenticationProvider - DaoAuthenticationProvider с настроенными UserDetailsService и PasswordEncoder.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        var provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    /**
     * @brief Конфигурирует цепочку фильтров безопасности.
     * @param http - объект для настройки безопасности веб-запросов.
     * @return SecurityFilterChain - цепочка фильтров безопасности.
     * @throws Exception при ошибках конфигурации.
     *
     * @details Настройки включают:
     * - Отключение CSRF и CORS защиты
     * - Настройку правил доступа для различных URL
     * - Конфигурацию формы входа
     * - Настройку выхода из системы
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(
                                "/", "/registration", "/login",
                                "/search", "/search_homepage", "/search_results",
                                "/search_results/**", "/hotel/**", "/api/cities/**"
                        ).permitAll()
                        .requestMatchers("/css/**", "/js/**", "/uploads/**").permitAll()
                        .requestMatchers("/staff/**").hasRole("STAFF")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginProcessingUrl("/login")
                        .loginPage("/login")
                        .usernameParameter("email") //!!!
                        //.defaultSuccessUrl("/search_homepage", true) // true не знаю зачем,
                        // но defaultSuccessUrl убрала, чтобы сохраняло,
                        // куда пользоавтель пытался перейти
                        .permitAll()
                )
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .permitAll()
                );
        return http.build();
    }
}