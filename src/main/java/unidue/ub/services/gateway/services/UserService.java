package unidue.ub.services.gateway.services;

import unidue.ub.services.gateway.model.Role;
import unidue.ub.services.gateway.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface UserService {

    void save(User user);

    void delete(Long id);

    User getUser(Long id);

    List<User> getAllUsers();

    User findByUsername(String username);

    User findByEmail(String email);

    User updatePassword(Long id, String newPassword);

    User updateRoles(Long id, Set<Role> roles);

    User updateFullname(Long id, String fullname);

    User updateEmail(Long id, String email);

    User applyChanges(Long id, Map<String, String> updates);
}
