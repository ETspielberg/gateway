package unidue.ub.services.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.session.data.redis.RedisFlushMode;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
@EnableWebSecurity
@EnableRedisHttpSession(redisFlushMode = RedisFlushMode.IMMEDIATE)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
    public WebSecurityConfiguration(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

    @Autowired
	private void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
			.withUser("user").password("password").roles("guest");
	}

	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.httpBasic().and()
		.formLogin().loginPage("/login").failureForwardUrl("/login?error").and()
		.logout().logoutUrl("/logout").logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK)).and()
		.authorizeRequests()
			.antMatchers("/index.html", "/login", "/register","/rss","/protokoll/**","/protokoll").permitAll()
			.antMatchers("/api").access("hasIpAddress('::1') or authenticated()")
			.antMatchers("/getter/**").access("hasIpAddress('::1') or authenticated()")
			.antMatchers("/files/**").access("hasIpAddress('::1') or authenticated()")
			.antMatchers("/services/**").access("hasIpAddress('::1') or authenticated()")
			.antMatchers("/admin/**").hasRole("ADMIN")
			.antMatchers("/fachref/**").hasRole("FACHREFERENT")
			.antMatchers("/mediamanagement/**").hasRole("MEDIA")
			.anyRequest().authenticated()
	    	.anyRequest().permitAll()
			.and()
		.csrf().disable();
			//.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
	}
}
