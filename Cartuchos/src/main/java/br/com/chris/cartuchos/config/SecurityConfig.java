package br.com.chris.cartuchos.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private DataSource dataSource;
	
	@Override
	public void configure(AuthenticationManagerBuilder builder)
			throws Exception{
		builder
			.jdbcAuthentication()
				.dataSource(dataSource)
				.passwordEncoder(new BCryptPasswordEncoder())
				.usersByUsernameQuery("select username, password, enabled from usuario where username = ? and enabled = true")
				.authoritiesByUsernameQuery("select username, role from usuario where username = ?");
			/*.inMemoryAuthentication()
				.withUser("chris").password("123").roles("USER")
				.and()
				.withUser("admin").password("321").roles("ADMIN");*/
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
			.formLogin().and()/*.loginPage("/login").permitAll()
			.failureUrl("/login?erro=true")
			.and()
			.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/login")
			.and()*/
			.authorizeRequests()
				.antMatchers(
						"/", 
						"/cartuchos", 
						"/departamentos", 
						"/registros"
						).hasAnyAuthority("USER", "ADMIN")
				.antMatchers("/admin", "/usuarios").hasAuthority("ADMIN")
				.anyRequest().authenticated()
				.and()
				.csrf().disable();
	}
	
}
