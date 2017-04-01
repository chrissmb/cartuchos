package br.com.chris.cartuchos.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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

@RestController
@RequestMapping("/cartuchos")
public class CartuchoController {
	
	@Autowired
	private CartuchoDao dao;
	
	@GetMapping()
	public ResponseEntity<List<Cartucho>> getAllCartuchos() {
		return new ResponseEntity<>(dao.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Cartucho> getCartucho(@PathVariable Long id) {
		return new ResponseEntity<>(dao.findOne(id), HttpStatus.OK);
	}
	
	@GetMapping(params = "descricao")
	public ResponseEntity<Cartucho> getCartuchoByDescricao(
			@RequestParam(value="descricao") String descricao) {
		return new ResponseEntity<>(dao.findByDescricao(descricao), HttpStatus.OK);
	}
	
	@GetMapping(params = "descricaoStartingWith")
	public ResponseEntity<List<Cartucho>> getCartuchoByDescricaoStartingWith(
			@RequestParam(value="descricaoStartingWith") String descricao) {
		return new ResponseEntity<>(dao.findByDescricaoStartingWith(descricao), HttpStatus.OK);
	}
	
	@PostMapping()
	/*public ResponseEntity<Cartucho> addCartucho(@Valid @RequestBody Cartucho cartucho, 
			BindingResult result) {
		if (descricaoExists(cartucho))
			result.addError(new FieldError("cartucho", "descricao", 
					"Catucho com descrição já cadastrada"));
		if (result.hasErrors())
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);*/
	public ResponseEntity<Cartucho> addCartucho(@RequestBody Cartucho cartucho) {
		return new ResponseEntity<>(dao.save(cartucho), HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Cartucho> updateCartucho(@RequestBody Cartucho cartucho, 
			@PathVariable Long id) {
		Cartucho cartuchoSave = new Cartucho();
		cartuchoSave.setId(id);
		cartuchoSave.setDescricao(cartucho.getDescricao());
		cartuchoSave.setQuantidade(cartucho.getQuantidade());
		cartuchoSave.setHabilitado(cartucho.isHabilitado());
		return new ResponseEntity<>(dao.save(cartuchoSave), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCartucho(@PathVariable Long id) {
		dao.delete(dao.findOne(id));
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	private boolean descricaoExists(Cartucho cartucho) {
		Cartucho cartuchoDb = dao.findByDescricao(cartucho.getDescricao());
		if (cartuchoDb == null)
			return false;
		return cartucho.getId() != cartuchoDb.getId();
	}
}
