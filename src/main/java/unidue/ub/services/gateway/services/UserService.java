package unidue.ub.services.gateway.services;

import unidue.ub.services.gateway.model.Role;
import unidue.ub.services.gateway.model.User;

import java.util.Set;

public interface UserService {

    public void save(User user);

    public void delete(User user);

    public User findByUsername(String username);

    public User updatePassword(Long id, String newPassword);

    public User updateRoles(Long id, Set<Role> roles);
}
