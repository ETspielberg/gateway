package unidue.ub.services.gateway.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import unidue.ub.services.gateway.model.User;

@Service
public class SecurityServiceImpl implements SecurityService{

    private final AuthenticationManager authenticationManager;

    private final DatabaseUserDetailsServiceImpl userService;

    @Autowired
    public SecurityServiceImpl(AuthenticationManager authenticationManager, DatabaseUserDetailsServiceImpl userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
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
