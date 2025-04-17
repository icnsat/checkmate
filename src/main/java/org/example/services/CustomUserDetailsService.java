package org.example.services;

import org.example.entities.CustomUserDetails;
import org.example.entities.User;
import org.example.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


/**
 * @brief Реализация сервиса загрузки пользовательских данных для Spring Security.
 * @details Класс реализует интерфейс UserDetailsService и используется Spring Security
 * для получения пользовательских данных по email (вместо username).
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

//    /** loadUserByUSERNAME на самом деле ищет пользователей по EMAIL.
//     * Поиск идет по почте, так как при регистрации указывается только она;
//     * Поля username у аккаунта нет.
//     * **/

    /**
     * @brief Загружает пользователя по email.
     * @details Метод переопределяет loadUserByUsername интерфейса UserDetailsService,
     * и ищет пользователя по email, а не по username.
     * Это сделано потому, что в системе регистрации используется только email.
     *
     * @param email Email пользователя, по которому производится поиск.
     * @return Объект UserDetails, представляющий пользователя.
     * @throws UsernameNotFoundException Если пользователь не найден.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        Optional<User> user = userRepository.findByUsername(username);
        Optional<User> user = userRepository.findByEmail(email);

//        if (user.isEmpty()){
//            throw new UsernameNotFoundException(email + "not found");
//        }
        return user.map(CustomUserDetails::new).orElseThrow(() -> new UsernameNotFoundException(email + " not found"));
    }
}