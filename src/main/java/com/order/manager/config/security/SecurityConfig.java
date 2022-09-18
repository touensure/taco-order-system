package com.order.manager.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
//		//The simplest protection, user predefined username and psw to login and use app
//		auth.inMemoryAuthentication()
//				.withUser("test1")
//				.password("p1")
//				.authorities("ROLE_USER")
//				.and()
//				.withUser("test2")
//				.password("p2")
//				.authorities("ROLE_USER");
		auth.userDetailsService(userDetailsService)
				.passwordEncoder(encoder());
	}

	@Override
	public void configure(HttpSecurity http) throws Exception{
		http.authorizeRequests()
				//for request "/design", "/orders" and "/abc", user should have role as 'ROLE_USER'
				.antMatchers("/design", "/orders", "/abc").access("hasRole('ROLE_USER')")
				//for requests, "/" and "/**", they are always permitted
				.antMatchers("/", "/**").access("permitAll")
				//and() indicates that former setting have been finished, and will add some other settings
				.and()
				/* for login needed requests, if user are not logged in current session,user will be redirected to the "/login"by default,
  				 * when login succeed, user will be redirected to the page before asked to log in,for example, user visit "/orders",
 				 * then springframework find this user not logged in,user will be redirected to "/login", then after login finished,
				 * user will be redirected to "/orders".*/
				.formLogin().loginPage("/login")
				/* if not setting defaultSuccessUrl, after user visit "/login" and log in succeed, user will be redirected to "/",
				 * to avoid this, just set it's defaultSuccessUrl.
				 *
				 * if "true" is set, after logged in, user should be redirected to "/design" no matter where "/login" is directed from*/
				.defaultSuccessUrl("/design", true)
				//when call "/logout", spring will log out automatically and redirect to logoutSuccessUrl without define "/logout" by developer
				.and().logout().logoutSuccessUrl("/");
	}
}
