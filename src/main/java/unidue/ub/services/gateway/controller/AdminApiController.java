package unidue.ub.services.gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import unidue.ub.services.gateway.model.Role;
import unidue.ub.services.gateway.model.User;
import unidue.ub.services.gateway.repository.RoleRepository;
import unidue.ub.services.gateway.services.UserService;

import java.util.List;
import java.util.Map;

@Controller
public class AdminApiController {

    @Autowired
    UserService userService;

    @Autowired
    RoleRepository roleRepository;

    @PatchMapping("/adminapi/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody Map<String, String> updates) {
        return ResponseEntity.ok(userService.applyChanges(id, updates));
    }

    @DeleteMapping("/adminapi/users/{id}")
    public void deleteUser(@PathVariable Long id){ userService.delete(id);
    }

    @PostMapping("/adminapi/users")
    public void saveUser(User user) {
        userService.save(user);
    }

    @GetMapping("/adminapi/roles")
    public ResponseEntity<List<Role>> getRoles() {
        return ResponseEntity.ok(roleRepository.findAllDistinct());
    }

    @GetMapping("/adminapi/rolenames")
    public ResponseEntity<List<Role>> getRoleNames() {
        List<Role> roles = roleRepository.findAllDistinct();
        for (Role role : roles) {
            role.setUsers(null);
        }
        return ResponseEntity.ok(roles);
    }

    @PostMapping("/adminapi/roles")
    public ResponseEntity<?> saveRole(Role role) {
        if (roleRepository.findByName(role.getName()) != null)
            return null;
        roleRepository.save(role);
        return ResponseEntity.ok(role);
    }

    @DeleteMapping("/adminapi/userroles")
    public ResponseEntity<?> deleteRole(Role role) {
        roleRepository.delete(role);
        return ResponseEntity.ok("done");
    }
}