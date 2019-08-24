package br.com.chris.cartuchos.config;

import java.util.Arrays;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

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
				.usersByUsernameQuery(
						"select username, password, enabled from usuario "
						+ "where username = ? and enabled = true")
				.authoritiesByUsernameQuery(
						"select username, role from usuario where username = ?");
			/*.inMemoryAuthentication()
				.withUser("chris").password("123456").roles("USER")
				.and()
				.withUser("admin").password("321").roles("ADMIN");*/
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
			.cors().and()
			.csrf().disable()
			.formLogin().loginPage("/login").permitAll()
			.failureUrl("/loginError")
			.and()
			/*.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/login")
			.and()*/
			.headers().frameOptions().disable().and() // resolve tela branca h2 console
			.authorizeRequests()
				.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
				.antMatchers(
						"/usuarios/logado",
						"/cartuchos", 
						"/cartuchos/*",
						"/departamentos",
						"/departamentos/*")
					.hasAnyAuthority("USER", "ADMIN")
				.antMatchers(
						"/admin", 
						"/usuarios",
						"/usuarios/*")
					.hasAuthority("ADMIN")
				.antMatchers(
						"/webjars/**",
						"/js/**")
					.permitAll()
				.antMatchers(HttpMethod.POST, "/xlogin").permitAll()
				.anyRequest().authenticated()
				.and()
				.httpBasic() //Libera autenticação basica (funciona no Postman)
				.and()
				// filtra requisições de login
				.addFilterBefore(new JWTLoginFilter("/xlogin", authenticationManager()),
		                UsernamePasswordAuthenticationFilter.class)
				
				// filtra outras requisições para verificar a presença do JWT no header
				.addFilterBefore(new JWTAuthenticationFilter(),
		                UsernamePasswordAuthenticationFilter.class);
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("*"));
		configuration.setExposedHeaders(Arrays.asList("Authorization","Cache-Control"));
		configuration.setAllowedHeaders(Arrays.asList("*"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
	
	/* @Override
    public void configure(WebSecurity web) throws Exception {
        web.debug(true);
    } */
	
}
