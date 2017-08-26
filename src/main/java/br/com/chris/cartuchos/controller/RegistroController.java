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
import org.springframework.web.bind.annotation.RestController;

import br.com.chris.cartuchos.model.Cartucho;
import br.com.chris.cartuchos.model.CartuchoDao;
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
	
	@GetMapping("/{id}")
	public ResponseEntity<Registro> getRegistro(@PathVariable Long id) {
		return new ResponseEntity<>(dao.findOne(id), HttpStatus.OK);
	}
	
	@PostMapping()
	public ResponseEntity<Registro> addRegistro(@RequestBody Registro registro) {
		Cartucho cartucho = cartuchoDao.findOne(registro.getCartucho().getId());
		int qtdRegistro = registro.getQuantidade();
		int qtdCartucho = cartucho.getQuantidade();
		
		if (registro.getOperacao().equals(Operacao.ENTRADA)) {
			cartucho.setQuantidade(qtdCartucho + qtdRegistro);
			cartuchoDao.save(cartucho);
		} else {
			if (qtdRegistro <= qtdCartucho) {
				cartucho.setQuantidade(qtdCartucho - qtdRegistro);
				cartuchoDao.save(cartucho);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
			}
		}
		return new ResponseEntity<>(dao.save(registro), HttpStatus.OK);
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
