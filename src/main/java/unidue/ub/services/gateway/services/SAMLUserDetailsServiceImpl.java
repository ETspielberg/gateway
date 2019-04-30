package unidue.ub.services.gateway.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.saml.SAMLCredential;
import org.springframework.security.saml.userdetails.SAMLUserDetailsService;
import org.springframework.stereotype.Service;
import unidue.ub.services.gateway.model.User;
import unidue.ub.services.gateway.repository.RoleRepository;
import unidue.ub.services.gateway.repository.UserRepository;

import java.util.Optional;


@Service
public class SAMLUserDetailsServiceImpl implements SAMLUserDetailsService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Autowired
    public SAMLUserDetailsServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    /**
     * returns a User object generated from the SAML credentials response xml
     * @param credential the ontained SAML credentials
     * @return the User object
     * @throws UsernameNotFoundException thrown if the nodes in the SAML credentials cannot be found
     */
    public User loadUserBySAML(SAMLCredential credential) throws UsernameNotFoundException {
        User user = new User();
        String username = getValue(credential,"uid");
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty()){
            user.setUsername(username);
            user.setEmail(getValue(credential, "mail"));
            user.setFullname(getValue(credential, "displayName"));
            user.addRole(roleRepository.findByName("ROLE_GUEST"));
            userRepository.save(user);
        } else
            user = userOptional.get();
        return user;
    }

    /**
     * retrieves the given node value from the SAML credentials xml
     * @param credential the obtained SAML credentials
     * @param node the name of the the attribute to be read
     * @return the string contents of the attribute
     */

    private String getValue(SAMLCredential credential, String node) {
        return credential.getAttributeAsString(node);
    }
}