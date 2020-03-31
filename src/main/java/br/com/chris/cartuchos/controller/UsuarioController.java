package br.com.chris.cartuchos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.chris.cartuchos.model.bean.Usuario;
import br.com.chris.cartuchos.model.bo.UsuarioBo;

@RestController
@RequestMapping("usuarios")
public class UsuarioController {
	
	@Autowired
	UsuarioBo bo;
	
	@GetMapping
	public ResponseEntity<List<Usuario>> getAllUsuario() {
		return new ResponseEntity<>(bo.getAllUsuario(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Usuario> getUsuario(@PathVariable Long id) {
		return new ResponseEntity<>(bo.getUsuario(id), HttpStatus.OK);
	}
	
	@GetMapping("/logado")
	public ResponseEntity<Usuario> getLogado() {
		return new ResponseEntity<>(bo.getLogado(), HttpStatus.OK);
	}
	
	@PutMapping("/logado")
	public ResponseEntity<Usuario> alteraSenha(@RequestBody Usuario usuario) {
		return new ResponseEntity<>(bo.alteraSenha(usuario), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Usuario> addUsuario(@RequestBody Usuario usuario) {
		return new ResponseEntity<>(bo.addUsuario(usuario), HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Usuario> updateUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
		return new ResponseEntity<>(bo.updateUsuario(id, usuario), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUsuario(@PathVariable Long id) {
		bo.deleteUsuario(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
