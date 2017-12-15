package unidue.ub.services.gateway.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import unidue.ub.services.gateway.model.Role;
import unidue.ub.services.gateway.repository.UserRepository;
import unidue.ub.services.gateway.repository.RoleRepository;
import unidue.ub.services.gateway.model.User;

import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        if (userRepository.findAll() == null)
            user.addRole(roleRepository.findByName("ROLE_ADMIN"));
        else
            user.addRole(roleRepository.findByName("ROLE_GUEST"));
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user.getId());
    }

    @Override
    public User updatePassword(Long id, String newPassword) {
        User user = userRepository.findById(id);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        logger.info("updated password to " + newPassword + " (== " + user.getPassword() + ")");
        return user;
    }

    @Override
    public User updateRoles(Long id, Set<Role> roles) {
        User user = userRepository.findById(id);
        user.setRoles(roles);
        userRepository.save(user);
        return user;
    }
}
