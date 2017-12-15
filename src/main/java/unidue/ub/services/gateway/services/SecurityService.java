package unidue.ub.services.gateway.services;

public interface SecurityService {

    public void autologin(String username, String password);

    public String findLoggedInUsername();
}
