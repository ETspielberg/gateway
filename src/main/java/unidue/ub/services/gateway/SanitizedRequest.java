package unidue.ub.services.gateway;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

@Component
public class SanitizedRequest extends HttpServletRequestWrapper {

    public SanitizedRequest(HttpServletRequest httpServletRequest) {
        super(httpServletRequest);
    }

    @Override
    public String getQueryString() {
        return super.getQueryString().replace("{","");
    }
}
