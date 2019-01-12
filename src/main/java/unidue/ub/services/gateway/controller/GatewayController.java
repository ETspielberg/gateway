package unidue.ub.services.gateway.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import unidue.ub.services.gateway.model.User;
import unidue.ub.services.gateway.services.SecurityService;
import unidue.ub.services.gateway.services.UserService;
import unidue.ub.services.gateway.services.UserValidator;

import java.security.Principal;
import java.util.*;

@Controller
public class GatewayController {

    private final UserService userService;

    private final SecurityService securityService;

    private final UserValidator userValidator;

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public GatewayController(UserService userService, SecurityService securityService, UserValidator userValidator) {
        this.userService = userService;
        this.securityService = securityService;
        this.userValidator = userValidator;
    }

    @RequestMapping("/activeuser")
    @CrossOrigin("http://localhost:4200")
    @ResponseBody
    public Map<String, Object> user(Principal principal) {
        try {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("name", principal.getName());
            map.put("roles", AuthorityUtils.authorityListToSet(((Authentication) principal)
                    .getAuthorities()));
            User user = userService.findByUsername(principal.getName());
            map.put("fullname",user.getFullname());
            map.put("email", user.getEmail());
            return map;
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping("/userdetails")
    public ResponseEntity<User> getCurrentUser(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        return ResponseEntity.ok(user);
    }

    @RequestMapping("/login")
    public String login() {
        return "forward:/";
    }

    @PostMapping("/newUser")
    public String newUser(@RequestBody Map<String, String> userdetails) {
        User user = new User();
        user.setPassword(userdetails.get("password"));
        user.setUsername(userdetails.get("username"));
        if (userdetails.get("email")!= null)
        user.setEmail(userdetails.get("email"));
        if (userdetails.get("fullname") != null)
            user.setFullname(userdetails.get("fullname"));
        if (!userValidator.validate(user)) {
            log.info("user not valid");
            throw new ForbiddenException();
        }
        userService.save(user);
        securityService.autologin(user.getUsername(), user.getPassword());
        return "success";
    }

    @PutMapping("/updateCurrentUser")
    public ResponseEntity<User> updateCurrentUser(@RequestBody Map<String, String> updates) {
        User user = userService.findByUsername(updates.get("username"));
        return ResponseEntity.ok(userService.applyChanges(user.getId(), updates));
    }

    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    private class ForbiddenException extends RuntimeException {
    }
}
