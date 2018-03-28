package unidue.ub.services.gateway.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unidue.ub.services.gateway.model.User;

@Component
public class UserValidator {
    @Autowired
    private UserServiceImpl userServiceImpl;

    private Logger log = LoggerFactory.getLogger(this.getClass());

    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    public boolean validate(User user) {
        if (user.getUsername().length() < 4 || user.getUsername().length() > 32) {
            return false;
        }
        User userFound = userServiceImpl.findByUsername(user.getUsername());
        if (userFound != null) {
            return false;
        }
        return true;
    }
}
