package unidue.ub.services.gateway;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import unidue.ub.services.gateway.model.Role;
import unidue.ub.services.gateway.model.User;
import unidue.ub.services.gateway.repository.RoleRepository;
import unidue.ub.services.gateway.repository.UserRepository;
import unidue.ub.services.gateway.services.*;

import javax.xml.ws.Response;
import java.io.IOException;
import java.security.Principal;
import java.util.*;

@Controller
public class GatewayController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/activeuser")
    @ResponseBody
    public Map<String, Object> user(Principal principal) {
        try {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("name", principal.getName());
            map.put("roles", AuthorityUtils.authorityListToSet(((Authentication) principal)
                    .getAuthorities()));
            return map;
        } catch (Exception e) {
            return null;
        }
    }

    @RequestMapping("/login")
    public String login() {
        return "forward:/";
    }

    @PostMapping("/newUser")
    @CrossOrigin(value = "http://localhost:8080")
    public String newUser(@RequestBody Map<String, String> userdetails) {
        User user = new User();
        user.setPassword(userdetails.get("password"));
        user.setUsername(userdetails.get("username"));
        if (!userValidator.validate(user)) {
            log.info("user not valid");
            return  "error";
        }
        userService.save(user);
        securityService.autologin(user.getUsername(), user.getPassword());
        return "success";
    }

    @GetMapping("/adminapi/users")
    public ResponseEntity<List<User>> getUsers() { return ResponseEntity.ok(userRepository.findAll());}

    @GetMapping("/adminapi/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) { return ResponseEntity.ok(userRepository.findById(id));}

    @PatchMapping("/adminapi/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody Map<String, String> updates) throws IOException {
        if (updates.get("newPassword") != null){
            User user = userService.updatePassword(id, updates.get("newPassword"));
            return ResponseEntity.ok(user);
        } else if (updates.get("roles") != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            Set<Role> roles = fromJSON(new TypeReference<Set<Role>>() {}, updates.get( "roles"));
            User user = userService.updateRoles(id, roles);
            return ResponseEntity.ok(user);
        }
        return null;
    }

    @DeleteMapping("/adminapi/users/{id}")
    public void deleteUser(@PathVariable Long id) { userRepository.delete(id);}

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
        roleRepository.delete(role.getId());
        return ResponseEntity.ok("done");
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
