package br.com.chris.cartuchos.model.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.chris.cartuchos.model.bean.Cartucho;
import br.com.chris.cartuchos.model.dao.CartuchoDao;

@Component
public class CartuchoBo {

	@Autowired
	private CartuchoDao dao;
	
	public List<Cartucho> getCatuchosAtivos() {
		return dao.findByAtivoTrue();
	}
	
	public List<Cartucho> getCartuchos() {
		return dao.findAll();
	}
	
	public Cartucho getById(Long id) {
		return dao.findById(id).get();
	}
	
	public List<Cartucho> getByDescricaoContem(String descricao) {
		return dao.findByDescricaoContaining(descricao);
	}
	
	public Cartucho addCartucho(Cartucho cartucho) {
		cartucho.setId(null);
		cartucho.setQuantidade(0);
		validaDescricao(cartucho);
		return dao.save(cartucho);
	}
	
	public Cartucho updateCartucho(Cartucho cartucho, Long id) {
		cartucho.setId(id);
		Cartucho cartuchoDb = dao.findById(id).get();
		if (cartuchoDb == null) {
			throw new RuntimeException("Cartucho inexitente.");
		}		
		cartucho.setQuantidade(cartuchoDb.getQuantidade());
		validaDescricao(cartucho);
		return dao.save(cartucho);
	}
	
	public void deleteCartucho(Long id) {
		dao.deleteById(id);
	}
	
	private void validaDescricao(Cartucho cartucho) {
		Cartucho cartuchoDB =  dao.findByDescricao(cartucho.getDescricao());
		if (cartuchoDB != null && (cartucho.getId() == null || !cartucho.equals(cartuchoDB)))
				throw new RuntimeException("Descrição já utilizada.");
	}
}
