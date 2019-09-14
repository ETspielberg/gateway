package org.unidue.ub.libintel.gateway.repository;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.unidue.ub.libintel.gateway.model.Role;
import org.unidue.ub.libintel.gateway.model.User;

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
