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

import br.com.chris.cartuchos.model.bean.Cartucho;
import br.com.chris.cartuchos.model.bo.CartuchoBo;

@RestController
@RequestMapping("/cartuchos")
public class CartuchoController {
	
	@Autowired
	private CartuchoBo bo;
	
	@GetMapping()
	public ResponseEntity<List<Cartucho>> getCartuchosAtivos() {
		return new ResponseEntity<>(bo.getCatuchosAtivos(), HttpStatus.OK);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Cartucho>> getAllCartuchos() {
		return new ResponseEntity<>(bo.getCartuchos(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Cartucho> getCartucho(@PathVariable Long id) {
		return new ResponseEntity<>(bo.getCartucho(id), HttpStatus.OK);
	}
	
	@GetMapping(params = "descricaoContem")
	public ResponseEntity<List<Cartucho>> getCartuchoByDescricaoContem(
			@RequestParam(value="descricaoStartingWith") String descricao) {
		return new ResponseEntity<>(bo.getByDescricaoContem(descricao), HttpStatus.OK);
	}
	
	@GetMapping("ativo")
	public ResponseEntity<List<Cartucho>> getCartuchoByAtivoTrue() {
		return getCartuchosAtivos();
	}
	
	@PostMapping()
	public ResponseEntity<Cartucho> addCartucho(@RequestBody Cartucho cartucho) {
		return new ResponseEntity<>(bo.addCartucho(cartucho), HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Cartucho> updateCartucho(@RequestBody Cartucho cartucho, 
			@PathVariable Long id) {		
		return new ResponseEntity<>(bo.updateCartucho(cartucho, id), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCartucho(@PathVariable Long id) {
		bo.deleteCartucho(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
