package br.com.chris.cartuchos.config;

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
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	private final String[] ALLOWED_CORS = {
			"https://cartuchos-angular.netlify.com",
			"https://cartuchos-angular.web.app",
			"https://cartuchos-angular.firebaseapp.com",
			"http://localhost:4200",
			"http://127.0.0.1:4200"
	};
	
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
						"select login as username, senha_hash as password, enabled "
						+ "from usuario "
						+ "where login = ? and enabled = true")
				.authoritiesByUsernameQuery(
						"select login as username, role from usuario where login = ?");
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
			//.cors().and()
			.csrf().disable()
			//.formLogin().loginPage("/login").permitAll()
			//.failureUrl("/loginError")
			//.and()
			/*.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/login")
			.and()*/
			//.headers().frameOptions().disable().and() // resolve tela branca h2 console
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
				.antMatchers(HttpMethod.POST, "/login").permitAll()
				.anyRequest().authenticated()
				.and()
				//.httpBasic() //Libera autenticação basica (funciona no Postman)
				//.and()
				// filtra requisições de login
				.addFilterBefore(new JWTLoginFilter("/login", authenticationManager()),
		                UsernamePasswordAuthenticationFilter.class)
				
				// filtra outras requisições para verificar a presença do JWT no header
				.addFilterBefore(new JWTAuthenticationFilter(),
		                UsernamePasswordAuthenticationFilter.class);
	}
	
	@Bean
	public WebMvcConfigurer corsConfigure() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
					.allowedOrigins(ALLOWED_CORS)
					.exposedHeaders("Authorization","Cache-Control");
			}
		};
	}
	
}
