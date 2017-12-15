package unidue.ub.services.gateway;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import unidue.ub.services.gateway.model.Role;
import unidue.ub.services.gateway.model.User;

/**
 * Created by Eike on 26.06.2017.
 */
@Configuration
@EnableRedisHttpSession
public class RestConfiguration extends RepositoryRestConfigurerAdapter {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(User.class,Role.class);
    }
}
