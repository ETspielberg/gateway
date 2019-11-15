package org.unidue.ub.libintel.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.unidue.ub.libintel.gateway.services.DatabaseUserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    public WebSecurityConfiguration(DatabaseUserDetailsServiceImpl userDetailsServiceImpl) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
	}

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    private final DatabaseUserDetailsServiceImpl userDetailsServiceImpl;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureAuthSource(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(bCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();
        http.httpBasic().disable();
        http
                .authorizeRequests().antMatchers(HttpMethod.POST, "/service/elisa/**").permitAll().and()
                .authorizeRequests().antMatchers(HttpMethod.GET, "/api/counterretrieval/ebookcounter/**").permitAll().and()
                //.hasIpAddress("132.252.181.87").and()
                .authorizeRequests()
                .antMatchers("/index.html", "/login", "/register", "/rss", "/libintelLogin").permitAll()
                .antMatchers("/error").permitAll()
                .antMatchers(HttpMethod.GET, "service/**").permitAll()
                .antMatchers(HttpMethod.GET, "/protokoll/**", "/protokoll", "/getter/**", "/glossar", "/glossar/**").permitAll()
                .antMatchers(HttpMethod.GET, "/files/viewer/**", "/files/public/**").permitAll()
                .antMatchers("/files/**", "/files").authenticated()
                .antMatchers("/api/**").access("hasIpAddress('::1') or isAuthenticated()")
                .antMatchers("/services/**").access("hasIpAddress('::1') or isAuthenticated()")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/system/**").hasRole("ADMIN")
                .antMatchers("/fachref/**").hasRole("FACHREFERENT")
                .antMatchers("/media/**").hasRole("MEDIA")
                .antMatchers("/bibliometrics/**").hasRole("BIBLIOMETRICS")
                .anyRequest().authenticated()
                .anyRequest().permitAll()
                .and()
                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).ignoringAntMatchers("/logout", "/files/counterbuilder", "/service/elisa/sendEav", "/service/elisa/receiveEav", "/libintelLogin");
        http.exceptionHandling().accessDeniedHandler(new AccessDeniedHandlerImpl())
                //.authenticationEntryPoint(getAuthEntryPoint())
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/libintelLogin")
                .defaultSuccessUrl("/start")
                .successHandler(restAuthenticationSuccessHandler())
                .failureForwardUrl("/login?error")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK))
                .permitAll();
    }

    @Bean
    public AuthenticationSuccessHandler restAuthenticationSuccessHandler(){
        return new RestAuthenticationSuccessHandler();
    }
}
