package br.com.chris.cartuchos.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Component
public class TokenAuthenticationService {

	// EXPIRATION_TIME = 10 dias
	static final long EXPIRATION_TIME = 860_000_000;
	static final String SECRET = "MySecret";
	static final String TOKEN_PREFIX = "Bearer";
	static final String HEADER_STRING = "Authorization";
	
	static void addAuthentication(HttpServletResponse response,
			String username, Collection<? extends GrantedAuthority> roles) {
		
		String JWT = Jwts.builder()
				.setSubject(username)
				.claim("roles", joinRoles(roles))
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SECRET)
				.compact();
		
		response.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
	}
	
	static String joinRoles(Collection<? extends GrantedAuthority> roles) {
		StringBuilder sb = new StringBuilder();
		for (GrantedAuthority role: roles) {
			if (sb.length() > 0)
				sb.append("-");
			sb.append(role.getAuthority());
		}
		return sb.toString();
	}
	
	static Authentication getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(HEADER_STRING);
		
		if (token != null) {
			String user, roles;
			try {
				
				Claims claims = Jwts.parser()
						.setSigningKey(SECRET)
						.parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
						.getBody();
				user = claims.getSubject();
				roles = claims.get("roles", String.class);
				
			} catch (Exception e) {
				return null;
			}
			
			if (user != null && roles != null) {
				User usuario = new User(user, "", getAuths(roles));
				return new UsernamePasswordAuthenticationToken(usuario, null, getAuths(roles));
			}
		}
		
		return null;
	}
	
	static private Collection<GrantedAuthority> getAuths(String roles) {
		Collection<GrantedAuthority> collection = new ArrayList<GrantedAuthority>();
		for (String role: roles.split(",")) {
			collection.add(new SimpleGrantedAuthority(role));
		}
		return collection;
	}
}
