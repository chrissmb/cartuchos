package br.com.chris.cartuchos;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Teste {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		System.out.println(encoder.encode("111111"));
	}

}
