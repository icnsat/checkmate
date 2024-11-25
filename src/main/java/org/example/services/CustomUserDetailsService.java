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

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    /** loadUserByUSERNAME на самом деле ищет пользователей по EMAIL.
     * Поиск идет по почте, так как при регистрации указывается только она;
     * Поля username у аккаунта нет.
     * **/
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
