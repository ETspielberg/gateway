package unidue.ub.services.gateway.services;

import org.opensaml.xml.schema.impl.XSAnyImpl;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.saml.SAMLCredential;
import org.springframework.security.saml.userdetails.SAMLUserDetailsService;
import org.springframework.stereotype.Service;
import unidue.ub.services.gateway.model.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class SAMLUserDetailsServiceImpl implements SAMLUserDetailsService {

    public Object loadUserBySAML(SAMLCredential credential)
            throws UsernameNotFoundException {
        XSAnyImpl uid =
                (XSAnyImpl) credential.getAttributes().stream()
                        .filter(a -> a.getFriendlyName().equals("uid"))
                        .findFirst().
                                orElseThrow(() -> new UsernameNotFoundException("uid not found from assertion"))
                        .getAttributeValues().get(0);

        List<GrantedAuthority> authorities = new ArrayList<>();
        User user = new User();
        user.setUsername(uid.getTextContent());
        return user;
    }

}
