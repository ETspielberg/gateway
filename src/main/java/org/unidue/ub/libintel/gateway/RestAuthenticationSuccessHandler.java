package org.unidue.ub.libintel.gateway;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * avoids sending 302 redirect responses, which do not worked with restful frontends. Instead, the redirect URL is read
 * from the session SPRING_SECURITY_DAVED_REQUEST parameter and added as header 'redirectUrl', which can be read by the
 * frontend.
 */
public class RestAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication) {
        // add the redirect URL header.
        DefaultSavedRequest defaultSavedRequest = (DefaultSavedRequest) request.getSession().getAttribute("SPRING_SECURITY_SAVED_REQUEST");
        String requestUrl = "/start";
        if( defaultSavedRequest != null)
            requestUrl = defaultSavedRequest.getRedirectUrl();
        response.addHeader("redirectUrl", requestUrl);

        // clear all authentification information from the request
        clearAuthenticationAttributes(request);
    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
}
