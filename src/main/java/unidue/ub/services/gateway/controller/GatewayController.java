package unidue.ub.services.gateway.controller;

import com.sun.mail.iap.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import unidue.ub.services.gateway.exceptions.ForbiddenException;
import unidue.ub.services.gateway.model.User;
import unidue.ub.services.gateway.services.DatabaseUserDetailsServiceImpl;
import unidue.ub.services.gateway.services.UserValidator;

import java.security.Principal;
import java.util.*;

@Controller
public class GatewayController {

    private final DatabaseUserDetailsServiceImpl userService;

    private final UserValidator userValidator;

    private final AuthenticationManager authenticationManager;

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public GatewayController(DatabaseUserDetailsServiceImpl userService, UserValidator userValidator, AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @RequestMapping("/activeuser")
    @ResponseBody
    public Map<String, Object> user(Principal principal) {
        try {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("name", principal.getName());
            map.put("roles", AuthorityUtils.authorityListToSet(((Authentication) principal)
                    .getAuthorities()));
            User user = userService.loadByUsername(principal.getName());
            map.put("fullname",user.getFullname());
            map.put("email", user.getEmail());
            return map;
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping("/userdetails")
    public ResponseEntity<User> getCurrentUser(Principal principal) {
        User user = userService.loadByUsername(principal.getName());
        return ResponseEntity.ok(user);
    }

    @PostMapping("/newUser")
    public ResponseEntity<?> newUser(@RequestBody Map<String, String> userdetails) {
        User user = new User();
        String password = userdetails.get("password");
        user.setPassword(password);
        user.setUsername(userdetails.get("username"));
        if (userdetails.get("email")!= null)
        user.setEmail(userdetails.get("email"));
        if (userdetails.get("fullname") != null)
            user.setFullname(userdetails.get("fullname"));
        if (!userValidator.validate(user)) {
            log.info("user not valid");
            return ResponseEntity.badRequest().body("user not valid");
        }
        userService.save(user);
        log.info("saved new user " + user.getUsername());
        UserDetails userDetails = userService.loadUserByUsername(user.getUsername());
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password,userDetails.getAuthorities());
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        if (usernamePasswordAuthenticationToken.isAuthenticated())
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        return ResponseEntity.ok("success");
    }

    @PutMapping("/updateCurrentUser")
    public ResponseEntity<User> updateCurrentUser(@RequestBody Map<String, String> updates) {
        User user = userService.loadByUsername(updates.get("username"));
        return ResponseEntity.ok(userService.applyChanges(user.getId(), updates));
    }
}
