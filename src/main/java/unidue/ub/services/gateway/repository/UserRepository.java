package unidue.ub.services.gateway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unidue.ub.services.gateway.model.User;

import java.util.Optional;

public interface UserRepository  extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String usrname);

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    void deleteUserById(Long Id);

}
