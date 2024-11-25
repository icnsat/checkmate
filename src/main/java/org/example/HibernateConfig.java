package org.example;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan("org.example")
@EnableJpaRepositories("org.example")
public class HibernateConfig {
}