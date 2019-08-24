package br.com.chris.cartuchos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import br.com.chris.cartuchos.model.Cartucho;
import br.com.chris.cartuchos.model.CartuchoDao;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/cartuchos")
public class CartuchoController {
	
	@Autowired
	private CartuchoDao dao;
	
	@GetMapping()
	public ResponseEntity<List<Cartucho>> getCartuchosAtivos() {
		return new ResponseEntity<>(dao.findByAtivoTrue(), HttpStatus.OK);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Cartucho>> getAllCartuchos() {
		return new ResponseEntity<>(dao.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Cartucho> getCartucho(@PathVariable Long id) {
		return new ResponseEntity<>(dao.findOne(id), HttpStatus.OK);
	}
	
	@GetMapping(params = "descricaoStartingWith")
	public ResponseEntity<List<Cartucho>> getCartuchoByDescricaoStartingWith(
			@RequestParam(value="descricaoStartingWith") String descricao) {
		return new ResponseEntity<>(dao.findByDescricaoStartingWith(descricao), HttpStatus.OK);
	}
	
	@GetMapping("ativo")
	public ResponseEntity<List<Cartucho>> getCartuchoByAtivoTrue() {
		return new ResponseEntity<>(dao.findByAtivoTrue(), HttpStatus.OK);
	}
	
	@PostMapping()
	public ResponseEntity<Cartucho> addCartucho(@RequestBody Cartucho cartucho) {
		if (cartucho.getId() == null) {
			cartucho.setQuantidade(0);
			return new ResponseEntity<>(dao.save(cartucho), HttpStatus.OK);
		}
		Cartucho cartuchoDb = dao.findOne(cartucho.getId());
		if (cartuchoDb != null) {
			cartucho.setQuantidade(cartuchoDb.getQuantidade());
		} else {
			cartucho.setQuantidade(0);
		}
		return new ResponseEntity<>(dao.save(cartucho), HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Cartucho> updateCartucho(@RequestBody Cartucho cartucho, 
			@PathVariable Long id) {
		cartucho.setId(id);
		Cartucho cartuchoDb = dao.findOne(id);
		if (cartuchoDb != null) {
			cartucho.setQuantidade(cartuchoDb.getQuantidade());
		} else {
			cartucho.setQuantidade(0);
		}		
		return new ResponseEntity<>(dao.save(cartucho), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCartucho(@PathVariable Long id) {
		dao.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
