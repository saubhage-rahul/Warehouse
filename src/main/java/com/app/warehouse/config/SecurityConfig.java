package com.app.warehouse.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService detailService;

	@Autowired
	private PasswordEncoder encoder;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(detailService).passwordEncoder(encoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// Authorization
		http.authorizeRequests().antMatchers("/rest/api/**", "/user/login", "/user/showForgot", "/user/reGenNewPwd")
				.permitAll().antMatchers("/uom/**", "/st/**", "/order/**", "/part/**", "/wh/**")
				.hasAnyAuthority("ADMIN", "APPUSER").antMatchers("/po/**", "/grn/**", "/sale/**", "/shiping/**")
				.hasAuthority("APPUSER").antMatchers("/user/register", "/user/create").hasAuthority("ADMIN")
				.anyRequest().authenticated()

				// Login Details
				.and().formLogin().loginPage("/user/login").loginProcessingUrl("/login")
				.defaultSuccessUrl("/user/setup", true).failureUrl("/user/login?error")

				// Logout Details
				.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/user/login?logout")

				// Exception Handling
				.and().exceptionHandling().accessDeniedPage("/user/denied");
	}
}
