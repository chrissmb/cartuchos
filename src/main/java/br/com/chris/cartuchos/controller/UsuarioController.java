package br.com.chris.cartuchos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.chris.cartuchos.model.bean.Usuario;
import br.com.chris.cartuchos.model.dao.UsuarioDao;

@RestController
@RequestMapping("usuarios")
public class UsuarioController {
	
	@Autowired
	UsuarioDao dao;
	
	private static int senhaTamanhoMin = 6;
	
	@GetMapping
	public ResponseEntity<List<Usuario>> getAllUsuario() {
		return new ResponseEntity<>(dao.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Usuario> getUsuario(@PathVariable Long id) {
		return new ResponseEntity<>(dao.findById(id).get(), HttpStatus.OK);
	}
	
	@GetMapping("/logado")
	public ResponseEntity<Usuario> getLogado() {
		UserDetails usuario = this.getUsuarioLogado();
		return new ResponseEntity<>(dao.findByUsername(usuario.getUsername()), HttpStatus.OK);
	}
	
	@PutMapping("/logado")
	public ResponseEntity<Usuario> alteraSenha(@RequestBody Usuario usuario) {
		if (usuario.getSenha().length() < senhaTamanhoMin)
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		
		UserDetails usr = this.getUsuarioLogado();
		Usuario usuarioDB = dao.findByUsername(usr.getUsername());
		
		if(usuarioDB == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		boolean eSenhaAtualValida = validaHash(usuario.getSenhaAtual(), usuarioDB.getPassword());
		if (!eSenhaAtualValida) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		usuarioDB.setPassword(hashSenha(usuario.getSenha()));
		usuarioDB.setSenha("");
		usuarioDB.setSenhaAtual("");
		return new ResponseEntity<>(dao.save(usuarioDB), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Usuario> addUsuario(@RequestBody Usuario usuario) {
		if (usuario.getSenha().length() < senhaTamanhoMin)
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		usuario.setPassword(hashSenha(usuario.getSenha()));
		usuario.setSenha("");
		return new ResponseEntity<>(dao.save(usuario), HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Usuario> updateUsuario(
			@PathVariable Long id,
			@RequestBody Usuario usuario) {
		if (usuario.getSenha().length() < senhaTamanhoMin) {
			Usuario usuarioDB = dao.findById(id).get();
			usuario.setPassword(usuarioDB.getPassword());
		} else {
			usuario.setPassword(hashSenha(usuario.getSenha()));
		}
		usuario.setId(id);
		usuario.setSenha("");
		return new ResponseEntity<>(dao.save(usuario), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUsuario(@PathVariable Long id) {
		dao.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	private String hashSenha(String senha) {
		PasswordEncoder bCrypt = new BCryptPasswordEncoder();
		return bCrypt.encode(senha);
	}
	
	private boolean validaHash(String senha, String hash) {
		PasswordEncoder bCrypt = new BCryptPasswordEncoder();
		return bCrypt.matches(senha, hash);
	}
	
	private UserDetails getUsuarioLogado() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return (UserDetails) auth.getPrincipal();
	}
}
