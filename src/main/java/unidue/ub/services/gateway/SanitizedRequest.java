package unidue.ub.services.gateway;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * http servlet request wrapper to sanitize bad requests by eliminating bad characters ("{") from query string
 */
@Component
public class SanitizedRequest extends HttpServletRequestWrapper {

    public SanitizedRequest(HttpServletRequest httpServletRequest) {
        super(httpServletRequest);
    }

    /**
     * If "{" is present in the request query string, it is removed
     * * @return
     */
    @Override
    public String getQueryString() {
        String queryString = super.getQueryString();
        if (queryString != null)
            if (queryString.contains("{"))
                queryString = queryString.replace("{", "");
        return queryString;
    }
}
