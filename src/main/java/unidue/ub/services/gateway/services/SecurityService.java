package unidue.ub.services.gateway.services;

import unidue.ub.services.gateway.model.User;

public interface SecurityService {

    void autologin(String username, String password);

    boolean checkCredentials(String username, String password);

    boolean checkPassword(String password);

    User getCurrentUser();
}
