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

import br.com.chris.cartuchos.model.Departamento;
import br.com.chris.cartuchos.model.DepartamentoDao;

@RestController
@RequestMapping("/departamentos")
public class DepartamentoController {
	
	@Autowired
	private DepartamentoDao dao;
	
	@GetMapping()
	public ResponseEntity<List<Departamento>> getAllDepartamento() {
		return new ResponseEntity<>(dao.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Departamento> getDepartamento(@PathVariable Long id) {
		return new ResponseEntity<>(dao.findOne(id), HttpStatus.OK);
	}
	
	@GetMapping(params = "descricao")
	public ResponseEntity<Departamento> getDepartamentoByDescricao(
			@RequestParam(value="descricao") String descricao) {
		return new ResponseEntity<>(dao.findByDescricao(descricao), HttpStatus.OK);
	}
	
	@PostMapping()
	public ResponseEntity<Departamento> addDepartamento(@RequestBody Departamento departamento) {
		if (departamento.getId() != null) {
			if (departamento.getId() == 1)
				return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}
		return new ResponseEntity<>(dao.save(departamento), HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Departamento> updateDepartamento(@RequestBody Departamento departamento, 
			@PathVariable Long id) {
		if (id == 1)
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		departamento.setId(id);
		return new ResponseEntity<>(dao.save(departamento), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteDepartamento(@PathVariable Long id) {
		if (id == 1)
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		dao.delete(dao.findOne(id));
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
