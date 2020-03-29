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

import br.com.chris.cartuchos.model.bean.Departamento;
import br.com.chris.cartuchos.model.bo.DepartamentoBo;

@RestController
@RequestMapping("/departamentos")
public class DepartamentoController {
	
	@Autowired
	private DepartamentoBo bo;
	
	@GetMapping()
	public ResponseEntity<List<Departamento>> getDepartamentosAtivos() {
		return new ResponseEntity<>(bo.getDepartamentosAtivos(), HttpStatus.OK);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Departamento>> getDepartamentos() {
		return new ResponseEntity<>(bo.getDepartamentos(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Departamento> getDepartamento(@PathVariable Long id) {
		return new ResponseEntity<>(bo.getDepartamento(id), HttpStatus.OK);
	}
	
	@GetMapping(params = "descricao")
	public ResponseEntity<Departamento> getDepartamentoByDescricao(
			@RequestParam(value="descricao") String descricao) {
		return new ResponseEntity<>(bo.getDepartamentoByDescricao(descricao), HttpStatus.OK);
	}
	
	@PostMapping()
	public ResponseEntity<Departamento> addDepartamento(@RequestBody Departamento departamento) {
		return new ResponseEntity<>(bo.addDepartamento(departamento), HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Departamento> updateDepartamento(@RequestBody Departamento departamento, 
			@PathVariable Long id) {
		return new ResponseEntity<>(bo.updateDepartamento(departamento, id), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteDepartamento(@PathVariable Long id) {
		bo.deleteDepartamento(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
