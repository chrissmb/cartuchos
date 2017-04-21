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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.chris.cartuchos.model.Usuario;
import br.com.chris.cartuchos.model.UsuarioDao;

@RestController
@RequestMapping("Usuarios")
public class UsuarioController {
	
	@Autowired
	UsuarioDao dao;
	
	@GetMapping
	public ResponseEntity<List<Usuario>> getAllUsuario() {
		return new ResponseEntity<>(dao.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("ativo")
	public ResponseEntity<List<Usuario>> getAllUsuarioEnabledTrue() {
		return new ResponseEntity<>(dao.findByEnabledTrue(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Usuario> getUsuario(@PathVariable Long id) {
		return new ResponseEntity<>(dao.findOne(id), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Usuario> addUsuario(@RequestBody Usuario usuario) {
		return new ResponseEntity<>(dao.save(usuario), HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Usuario> updateUsuario(
			@PathVariable Long id,
			@RequestParam Usuario usuario) {
		usuario.setId(id);
		return new ResponseEntity<>(dao.save(usuario), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUsuario(@PathVariable Long id) {
		dao.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
