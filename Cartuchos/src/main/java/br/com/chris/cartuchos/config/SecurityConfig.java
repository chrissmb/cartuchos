package br.com.chris.cartuchos.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Override
	public void configure(AuthenticationManagerBuilder builder)
			throws Exception{
		builder
			.inMemoryAuthentication()
			.withUser("chris").password("123").roles("USER")
			.and()
			.withUser("admin").password("321").roles("ADMIN");
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			.antMatchers("/**").hasAuthority("USER")
			.anyRequest().authenticated();
	}
}
