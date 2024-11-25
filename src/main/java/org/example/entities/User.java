package org.example.entities;

import jakarta.persistence.*;

/**Для аннотаций NotBlank**/
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Email обязателен")
    @Email(message = "Введите корректный email")
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @NotBlank(message = "Пароль обязателен")
    //@Size(min = 6, message = "Пароль должен быть не менее 6 символов")
    @Column(name = "password", nullable = false)
    private String password;

    //@Column(name = "first_name")
    //private String firstName;

    //@Column(name = "last_name")
    //private String lastName;

    //@Column(name = "phone_number")
    //private String phoneNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn( name = "role_id", nullable = false)
    private Role role;




    // Конструкторы, геттеры и сеттеры
    public User() {}

    public User(String email, String password, Role role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}