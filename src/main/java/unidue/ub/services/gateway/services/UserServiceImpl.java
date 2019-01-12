package unidue.ub.services.gateway.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import unidue.ub.services.gateway.model.Role;
import unidue.ub.services.gateway.model.User;
import unidue.ub.services.gateway.repository.RoleRepository;
import unidue.ub.services.gateway.repository.UserRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        if (userRepository.findAll().size() == 0)
            user.addRole(roleRepository.findByName("ROLE_ADMIN"));
        else
            user.addRole(roleRepository.findByName("ROLE_GUEST"));
        userRepository.save(user);
    }

    @Override
    public User getUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteUserById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updatePassword(Long id, String newPassword) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setPassword(bCryptPasswordEncoder.encode(newPassword));
            userRepository.save(user);
            return user;
        } else
            return null;
    }

    @Override
    public User updateFullname(Long id, String fullname) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setFullname(fullname);
            userRepository.save(user);
            return user;
        } else
            return null;
    }

    @Override
    public User updateEmail(Long id, String email) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setEmail(email);
            userRepository.save(user);
            return user;
        } else
            return null;
    }

    @Override
    public User updateRoles(Long id, Set<Role> roles) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setRoles(roles);
            userRepository.save(user);
            return user;
        } else
            return null;
    }

    @Override
    public User applyChanges(Long id, Map<String, String> updates) {
        if (updates.get("newPassword") != null) {
            log.info("setting new Password");
            return updatePassword(id, updates.get("newPassword"));
        } else if (updates.get("roles") != null) {
            Set<Role> roles = fromJSON(new TypeReference<Set<Role>>() {
            }, updates.get("roles"));
            return updateRoles(id, roles);
        } else if (updates.get("fullname") != null) {
            log.info("setting full name");
            return updateFullname(id, updates.get("fullname"));
        }
        else if (updates.get("email") != null) {
            log.info("setting email");
            return updateEmail(id, updates.get("email"));
        }
        return null;
    }

    private <T> T fromJSON(final TypeReference<T> type,
                           final String jsonPacket) {
        T data = null;

        try {
            data = new ObjectMapper().readValue(jsonPacket, type);
        } catch (Exception e) {
            log.info("could deserialize JSON");
        }
        return data;
    }
}
