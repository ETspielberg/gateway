package unidue.ub.services.gateway.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unidue.ub.services.gateway.model.User;

/**
 * Checks, whether the given User object is valid, that is the username has an appropriate length, is of User class and
 * is not already within the database
 */
@Service
public class UserValidator {

    private final UserServiceImpl userServiceImpl;

    @Autowired
    public UserValidator(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    /**
     * Checks, whether the given object class belongs to the User class
     * @param aClass the class of the object
     * @return true, if the object is of class User
     */
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    /**
     * Checks whether the username is between 4 and 32 characters long.
     * Checks whether the username is not already contained in the database.
     *
     * @param user user to be be validated
     * @return true, if the username has an appropriate length and if the user is not in the database
     */
    public boolean validate(User user) {
        if (user.getUsername().length() < 4 || user.getUsername().length() > 32) {
            return false;
        }
        User userFound = userServiceImpl.findByUsername(user.getUsername());
        return userFound == null;
    }
}
