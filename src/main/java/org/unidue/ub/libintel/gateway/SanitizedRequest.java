package org.unidue.ub.libintel.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.HashMap;
import java.util.Map;

/**
 * http servlet request wrapper to sanitize bad requests by eliminating bad characters ("{") from query string
 */
@Component
public class SanitizedRequest extends HttpServletRequestWrapper {

    public SanitizedRequest(HttpServletRequest httpServletRequest) {
        super(httpServletRequest);
    }

    public Logger log = LoggerFactory.getLogger(SanitizedRequest.class);

    /**
     * If "{" is present in the request query string, it is removed
     * * @return
     */
    @Override
    public String getQueryString() {
        String queryString = super.getQueryString();
        queryString = cleanUpString(queryString);
        return queryString;
    }

    @Override
    public String getParameter(String name) {
        String parameter = super.getParameter(name);
        parameter= cleanUpString(parameter);
        return parameter;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> paramterMapClean = new HashMap<>();
        Map<String, String[]> paramterMap = super.getParameterMap();
        paramterMap.forEach((key, value) -> {
            for (int i = 0; i<value.length; i++) {
                value[i] = cleanUpString(value[i]);
            }
            paramterMapClean.put(key, value);
        });
        return paramterMapClean;
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] parameterValues = super.getParameterValues(name);
        for (int i = 0; i<parameterValues.length; i++) {
            parameterValues[i] = cleanUpString(parameterValues[i]);
        }
        return parameterValues;
    }

    private String cleanUpString(String queryString) {
        if (queryString != null) {
            if (queryString.contains("{"))
                queryString = queryString.replace("{", "");
            if (queryString.contains("%0A"))
                queryString = queryString.replace("%0A", "+");
            if (queryString.contains("%0D"))
                queryString = queryString.replace("%0D", "+");
            if (queryString.contains("\n"))
                queryString = queryString.replace("\n", "+");
            if (queryString.contains("\r"))
                queryString = queryString.replace("\r", "+");
        }
        return queryString;
    }

}
