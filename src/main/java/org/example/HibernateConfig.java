package org.example;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @class HibernateConfig
 * @brief Конфигурационный класс для настройки Hibernate и Spring Data JPA.
 *
 * Этот класс выполняет следующие функции:
 * - Активирует механизм сканирования компонентов Spring
 * - Включает поддержку JPA репозиториев
 * - Настраивает базовую конфигурацию для работы с Hibernate
 *
 * @note Для работы требуется правильно настроенный файл application.properties/yml
 *       с параметрами подключения к БД.
 *
 * @author Елизавета Горновова
 * @version 1.0
 * @date 17.04.25
 */
@Configuration
@ComponentScan("org.example")
@EnableJpaRepositories("org.example")
public class HibernateConfig {
}