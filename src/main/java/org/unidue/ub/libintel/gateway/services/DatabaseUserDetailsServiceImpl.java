package org.unidue.ub.libintel.gateway.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.unidue.ub.libintel.gateway.repository.RoleRepository;
import org.unidue.ub.libintel.gateway.repository.UserRepository;
import org.unidue.ub.libintel.gateway.model.Role;
import org.unidue.ub.libintel.gateway.model.User;

import javax.security.auth.login.FailedLoginException;
import java.util.*;

@Service
public class DatabaseUserDetailsServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    private Logger log = LoggerFactory.getLogger(DatabaseUserDetailsServiceImpl.class);

    @Autowired
    public DatabaseUserDetailsServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }


    @Override
    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        if (userRepository.findAll().size() == 0)
            user.addRole(roleRepository.findByName("ROLE_ADMIN"));
        else
            user.addRole(roleRepository.findByName("ROLE_GUEST"));
        user.setEmail(user.getEmail().toLowerCase());
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
    public User loadByUsername(String username) {
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
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        User user;
        if (username.contains("@"))
            user = findByEmail(username);
        else
            user = loadByUsername(username);
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role : user.getRoles())
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }

    @Override
    public User updatePassword(User user, String oldPassword, String newPassword) throws FailedLoginException {
        log.info("comparing old password " + user.getPassword() + " with new one " + bCryptPasswordEncoder.encode(oldPassword));
        if (!bCryptPasswordEncoder.matches(oldPassword, user.getPassword()))
            throw new FailedLoginException("old passwort is not correct");
        user.setPassword(bCryptPasswordEncoder.encode(newPassword));
        userRepository.save(user);
        return user;
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
    @Transactional
    public User applyChanges(Long id, Map<String, String> updates) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty())
            throw new UsernameNotFoundException("the user could not be found");
        User user = userOptional.get();
        if (updates.get("roles") != null) {
            Set<Role> roles = fromJSON(new TypeReference<Set<Role>>() {
            }, updates.get("roles"));
            user.setRoles(roles);
        }
        if (updates.get("fullname") != null) {
            log.info("setting full name");
            user.setFullname(updates.get("fullname"));
        }
        if (updates.get("email") != null) {
            log.info("setting email");
            user.setEmail(updates.get("email"));
        }
        userRepository.save(user);
        return user;
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
