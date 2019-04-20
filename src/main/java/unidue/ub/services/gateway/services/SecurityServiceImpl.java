package unidue.ub.services.gateway.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import unidue.ub.services.gateway.model.User;

@Service
public class SecurityServiceImpl implements SecurityService {

    private final AuthenticationManager authenticationManager;

    private final DatabaseUserDetailsServiceImpl userService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    public SecurityServiceImpl(AuthenticationManager authenticationManager, DatabaseUserDetailsServiceImpl userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void autologin(String username, String password) {
        UserDetails userDetails = userService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        if (usernamePasswordAuthenticationToken.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
    }

    @Override
    public boolean checkCredentials(String username, String password) {
        UserDetails userDetails = userService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        return usernamePasswordAuthenticationToken.isAuthenticated();
    }

    public boolean checkPassword(String password) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof org.springframework.security.core.userdetails.User) {
                UserDetails userDetails = userService.loadUserByUsername(authentication.getName());
                return bCryptPasswordEncoder.encode(password).equals(userDetails.getPassword());
            }
        }
        return false;
    }

    @Override
    public User getCurrentUser() {
        /*SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof org.springframework.security.core.userdetails.User) {
                return (User) authentication.getPrincipal();
            } else if (authentication.getPrincipal() instanceof LdapUserDetailsImpl) {
                String dn = ((LdapUserDetailsImpl) authentication.getPrincipal()).getDn();
                return new User(dn, "", Arrays.asList());
                return null;
            }
        }
        throw new IllegalStateException("User not found!");
        */
        return null;
    }
}
