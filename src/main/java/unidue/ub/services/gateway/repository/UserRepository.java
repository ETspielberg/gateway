package unidue.ub.services.gateway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unidue.ub.services.gateway.model.User;

import java.util.List;

public interface UserRepository  extends JpaRepository<User,Long> {

    public User findByUsername(String email);

    public User findById(Long id);

}
