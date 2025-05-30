package com.example.siwbooks.repository;

import com.example.siwbooks.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Para el login por username.
     */
    Optional<User> findByUsername(String username);

    /**
     * Para comprobar duplicados al registrar un usuario.
     */
    Optional<User> findByEmail(String email);


}


