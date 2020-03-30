package br.com.chris.cartuchos.controller;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.chris.cartuchos.model.bean.Registro;
import br.com.chris.cartuchos.model.bo.RegistroBo;

@RestController
@RequestMapping("/registros")
public class RegistroController {
	
	@Autowired
	private RegistroBo bo;
	
	@GetMapping()
	public ResponseEntity<List<Registro>> getAllRegistros() {
		return new ResponseEntity<>(bo.getAllRegistros(), HttpStatus.OK);
	}
	
	@GetMapping("/pageable")
	public ResponseEntity<Page<Registro>> getAllRegistrosPageable(
			Pageable pageable,
			@RequestParam(name = "dtinicio", required = false)
		    @DateTimeFormat(pattern = "dd-MM-yyyy")
		    Calendar dataInicio,
		    @RequestParam(name = "dtfim", required = false)
		    @DateTimeFormat(pattern = "dd-MM-yyyy")
		    Calendar dataFim
	) {
		return new ResponseEntity<>(bo.getAllRegistrosPageable(pageable, dataInicio, dataFim), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Registro> getRegistro(@PathVariable Long id) {
		return new ResponseEntity<>(bo.getRegistro(id), HttpStatus.OK);
	}
	
	@PostMapping()
	public ResponseEntity<Registro> addRegistro(@RequestBody Registro registro) {
		return new ResponseEntity<>(bo.addRegistro(registro), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteRegistro(@PathVariable Long id) {
		bo.deleteRegistro(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
