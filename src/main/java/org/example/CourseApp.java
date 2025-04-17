package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @class CourseApp
 * @brief Главный класс Spring Boot приложения.
 *
 * Этот класс является точкой входа в приложение и содержит:
 * - Основную конфигурацию Spring Boot
 * - Метод main() для запуска приложения
 * - Автоматическую настройку компонентов Spring
 *
 * @note Приложение использует аннотацию @SpringBootApplication, которая объединяет:
 *       - @Configuration
 *       - @EnableAutoConfiguration
 *       - @ComponentScan
 *
 * @warning Для корректной работы все компоненты приложения должны находиться
 *          в пакете org.example или его подпакетах
 *
 * @author Елизавета Горновова
 * @version 1.0
 * @date 17.04.25
 */
@SpringBootApplication
public class CourseApp {
    public static void main(String[] args) {
        SpringApplication.run(CourseApp.class, args);
    }
}