package unidue.ub.services.gateway.services;

import unidue.ub.services.gateway.model.Role;
import unidue.ub.services.gateway.model.User;

import javax.security.auth.login.FailedLoginException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface UserService {

    void save(User user);

    void delete(Long id);

    User getUser(Long id);

    List<User> getAllUsers();

    User loadByUsername(String username);

    User findByEmail(String email);

    User updatePassword(User user, String oldPassword, String newPassword) throws FailedLoginException;

    User updateRoles(Long id, Set<Role> roles);

    User applyChanges(Long id, Map<String, String> updates);
}
