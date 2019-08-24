package br.com.chris.cartuchos.controller;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.chris.cartuchos.model.Cartucho;
import br.com.chris.cartuchos.model.CartuchoDao;
import br.com.chris.cartuchos.model.Departamento;
import br.com.chris.cartuchos.model.Operacao;
import br.com.chris.cartuchos.model.Registro;
import br.com.chris.cartuchos.model.RegistroDao;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/registros")
public class RegistroController {
	
	@Autowired
	private RegistroDao dao;
	
	@Autowired
	private CartuchoDao cartuchoDao;
	
	@GetMapping()
	public ResponseEntity<List<Registro>> getAllRegistros() {
		return new ResponseEntity<>(dao.findAll(), HttpStatus.OK);
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
		if (dataInicio != null && dataFim != null) {
			dataInicio.set(Calendar.HOUR_OF_DAY, 0);
			dataInicio.set(Calendar.MINUTE, 0);
			dataInicio.set(Calendar.SECOND, 0);
			dataFim.set(Calendar.HOUR_OF_DAY, 23);
			dataFim.set(Calendar.MINUTE, 59);
			dataFim.set(Calendar.SECOND, 59);
			return new ResponseEntity<>(dao.findByDataBetween(dataInicio, dataFim, pageable), HttpStatus.OK);
		}
		return new ResponseEntity<>(dao.findAll(pageable), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Registro> getRegistro(@PathVariable Long id) {
		return new ResponseEntity<>(dao.findOne(id), HttpStatus.OK);
	}
	
	@PostMapping()
	public ResponseEntity<Registro> addRegistro(@RequestBody Registro registro) {
		Cartucho cartucho = cartuchoDao.findOne(registro.getCartucho().getId());
		if (cartucho == null)
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		int qtdRegistro = registro.getQuantidade();
		int qtdCartucho = cartucho.getQuantidade();
		
		if (registro.getOperacao().equals(Operacao.ENTRADA)) {
			cartucho.setQuantidade(qtdCartucho + qtdRegistro);
			registro.setDepartamento(new Departamento());
			registro.getDepartamento().setId(1L);
			cartuchoDao.save(cartucho);
		} else {
			if (registro.getDepartamento() == null)
				return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
			if (registro.getDepartamento().getId() == 1L)
				return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
			if (qtdRegistro <= qtdCartucho) {
				cartucho.setQuantidade(qtdCartucho - qtdRegistro);
				cartuchoDao.save(cartucho);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
			}
		}
		registro.setData(Calendar.getInstance());
		return new ResponseEntity<>(dao.save(registro), HttpStatus.CREATED);
	}
	
	/*@PutMapping("/{id}")
	public ResponseEntity<Registro> updateRegistro(@RequestBody Registro registro,
			@PathVariable Long id) {
		registro.setId(id);
		return new ResponseEntity<>(dao.save(registro), HttpStatus.OK);
	}*/
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteRegistro(@PathVariable Long id) {
		dao.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
