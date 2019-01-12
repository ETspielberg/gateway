package unidue.ub.services.gateway.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import unidue.ub.services.gateway.model.User;

@Component
public class UserValidator {

    private final UserServiceImpl userServiceImpl;

    @Autowired
    public UserValidator(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    public boolean validate(User user) {
        if (user.getUsername().length() < 4 || user.getUsername().length() > 32) {
            return false;
        }
        User userFound = userServiceImpl.findByUsername(user.getUsername());
        return userFound == null;
    }
}
