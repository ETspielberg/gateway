package org.unidue.ub.libintel.gateway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unidue.ub.libintel.gateway.model.User;

import java.util.Optional;

public interface UserRepository  extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    void deleteUserById(Long Id);

}
