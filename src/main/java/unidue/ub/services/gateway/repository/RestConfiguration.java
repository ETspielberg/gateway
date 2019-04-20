package unidue.ub.services.gateway.repository;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import unidue.ub.services.gateway.model.Role;
import unidue.ub.services.gateway.model.User;

/**
 * Created by Eike on 26.06.2017.
 */
@Configuration
public class RestConfiguration extends RepositoryRestConfigurerAdapter {

    /**
     * Exposing the Ids for the users and roles
     * @param config the repository rest configuration object
     */
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(User.class,Role.class);
    }
}
