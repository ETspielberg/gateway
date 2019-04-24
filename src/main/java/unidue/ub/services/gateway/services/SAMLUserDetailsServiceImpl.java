package unidue.ub.services.gateway.services;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.saml.SAMLCredential;
import org.springframework.security.saml.userdetails.SAMLUserDetailsService;
import org.springframework.stereotype.Service;
import unidue.ub.services.gateway.model.User;

/*
@Service
public class SAMLUserDetailsServiceImpl implements SAMLUserDetailsService {

    /**
     * returns a User object generated from the SAML credentials response xml
     * @param credential the ontained SAML credentials
     * @return the User object
     * @throws UsernameNotFoundException thrown if the nodes in the SAML credentials cannot be found
     */
/*    public User loadUserBySAML(SAMLCredential credential) throws UsernameNotFoundException {
        User user = new User();
        user.setUsername(getValue(credential,"uid"));
        user.setEmail(getValue(credential, "mail"));
        user.setFullname(getValue(credential, "displayName"));
        return user;
    }

    /**
     * retrieves the given node value from the SAML credentials xml
     * @param credential the obtained SAML credentials
     * @param node the name of the the attribute to be read
     * @return the string contents of the attribute
     */

/*    private String getValue(SAMLCredential credential, String node) {
        XSAnyImpl xmlSchema = (XSAnyImpl) credential.getAttributes().stream()
                .filter(a -> a.getFriendlyName().equals(node))
                .findFirst().orElseThrow(() -> new UsernameNotFoundException("uid not found from assertion"))
                .getAttributeValues().get(0);
        return xmlSchema.getTextContent();
    }

}
*/