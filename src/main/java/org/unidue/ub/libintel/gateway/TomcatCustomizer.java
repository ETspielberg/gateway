package org.unidue.ub.libintel.gateway;

import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

@Component
public class TomcatCustomizer implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {

    /**
     * allowing for bad characters ("{}|") in query string
     * @param factory customize tomcat factory to allow for query chars '{}|' in web requests
     */
    public void customize(TomcatServletWebServerFactory factory) {
        factory.addConnectorCustomizers((TomcatConnectorCustomizer) connector -> connector.setAttribute("relaxedQueryChars", "{}|"));
    }
}
