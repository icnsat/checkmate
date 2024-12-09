package org.example.repositories;

import org.example.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByEmail(String email);
    Optional<User> findByEmail(String email);

    List<User> findAll();
}
