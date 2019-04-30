/*
* thanks to https://hellokoding.com/registration-and-login-example-with-spring-security-spring-boot-spring-data-jpa-hsql-jsp/
* and https://spring.io/guides/tutorials/spring-security-and-angular-js/
*/
package unidue.ub.services.gateway;

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableZuulProxy
@EnableEurekaClient
@EntityScan({"unidue.ub.services.gateway.model"})
@EnableGlobalMethodSecurity(securedEnabled = true)
public class GatewayApplication {

	@Value("${tomcat.ajp.port}")
	int ajpPort;

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	/**
	 * adding the AJP connector to bind to the Apache httpd on the server.
	 * @return customizer to add ajp connector to tomcat server.
	 */
	@Bean
	public WebServerFactoryCustomizer<TomcatServletWebServerFactory> servletContainer() {
		return server -> {
			if (server != null) {
				server.addAdditionalTomcatConnectors(redirectConnector());
			}
		};
	}

	private Connector redirectConnector() {
		Connector ajpConnector = new Connector("AJP/1.3");
		ajpConnector.setPort(ajpPort);
		ajpConnector.setSecure(false);
		ajpConnector.setAllowTrace(false);
		ajpConnector.setScheme("http");
		ajpConnector.setAttribute("packetSize", 65536);
		return ajpConnector;
	}
}
