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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.chris.cartuchos.model.Usuario;
import br.com.chris.cartuchos.model.UsuarioDao;

@CrossOrigin(origins = "*")
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
		return new ResponseEntity<>(dao.findOne(id), HttpStatus.OK);
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
		
		Usuario usuarioDB = dao.findOne(usuario.getId());
		boolean eSenhaAtualValida = validaHash(usuario.getSenhaAtual(), usuarioDB.getPassword());
		if (!eSenhaAtualValida) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		usuario.setPassword(hashSenha(usuario.getSenha()));
		usuario.setSenha("");
		usuario.setSenhaAtual("");
		return new ResponseEntity<>(dao.save(usuario), HttpStatus.OK);
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
			Usuario usuarioDB = dao.findOne(id);
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
		dao.delete(id);
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