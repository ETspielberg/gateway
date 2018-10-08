package unidue.ub.services.gateway.services;

import unidue.ub.services.gateway.model.User;

public interface SecurityService {

    void autologin(String username, String password);

    User getCurrentUser();
}
