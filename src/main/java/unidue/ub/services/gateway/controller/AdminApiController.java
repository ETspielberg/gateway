package unidue.ub.services.gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import unidue.ub.services.gateway.model.Role;
import unidue.ub.services.gateway.model.User;
import unidue.ub.services.gateway.repository.RoleRepository;
import unidue.ub.services.gateway.services.UserService;
import unidue.ub.services.gateway.services.DatabaseUserDetailsServiceImpl;

import java.util.List;
import java.util.Map;

@Controller
public class AdminApiController {

    private final
    UserService userService;

    private final
    RoleRepository roleRepository;

    @Autowired
    public AdminApiController(DatabaseUserDetailsServiceImpl userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/adminapi/users")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/adminapi/users/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @PatchMapping("/adminapi/users/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody Map<String, String> updates) {
        return ResponseEntity.ok(userService.applyChanges(id, updates));
    }

    @DeleteMapping("/adminapi/users/{id}")
    @Secured("ROLE_ADMIN")
    public void deleteUser(@PathVariable Long id){ userService.delete(id);
    }

    @PostMapping("/adminapi/users")
    @Secured("ROLE_ADMIN")
    public void saveUser(User user) {
        userService.save(user);
    }

    @GetMapping("/adminapi/roles")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<List<Role>> getRoles() {
        return ResponseEntity.ok(roleRepository.findAllDistinct());
    }

    @GetMapping("/adminapi/rolenames")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<List<Role>> getRoleNames() {
        List<Role> roles = roleRepository.findAllDistinct();
        for (Role role : roles) {
            role.setUsers(null);
        }
        return ResponseEntity.ok(roles);
    }

    @PostMapping("/adminapi/roles")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> saveRole(Role role) {
        if (roleRepository.findByName(role.getName()) != null)
            return null;
        roleRepository.save(role);
        return ResponseEntity.ok(role);
    }

    @DeleteMapping("/adminapi/userroles")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> deleteRole(Role role) {
        roleRepository.delete(role);
        return ResponseEntity.ok("done");
    }
}
