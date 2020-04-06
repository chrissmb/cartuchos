package br.com.chris.cartuchos.model.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.chris.cartuchos.model.NegocioException;
import br.com.chris.cartuchos.model.Operacao;
import br.com.chris.cartuchos.model.bean.Cartucho;
import br.com.chris.cartuchos.model.dao.CartuchoDao;

@Service
public class CartuchoBo {

	@Autowired
	private CartuchoDao dao;
	
	public List<Cartucho> getCatuchosAtivos() {
		return dao.findByAtivoTrue();
	}
	
	public List<Cartucho> getCartuchos() {
		return dao.findAll();
	}
	
	public Cartucho getCartucho(Long id) {
		return dao.findById(id).get();
	}
	
	public List<Cartucho> getByDescricaoContem(String descricao) {
		return dao.findByDescricaoContaining(descricao);
	}
	
	@Transactional
	public Cartucho addCartucho(Cartucho cartucho) {
		cartucho.setId(null);
		cartucho.setQuantidade(0);
		validaDescricao(cartucho);
		return dao.save(cartucho);
	}
	
	@Transactional
	public Cartucho updateCartucho(Cartucho cartucho, Long id) {
		cartucho.setId(id);
		Cartucho cartuchoDb = dao.findById(id).get();
		if (cartuchoDb == null) {
			throw new NegocioException("Cartucho inexistente.");
		}		
		cartucho.setQuantidade(cartuchoDb.getQuantidade());
		validaDescricao(cartucho);
		return dao.save(cartucho);
	}
	
	@Transactional
	public Cartucho estoqueCartucho(Long idCartucho, int quantidade, Operacao operacao) {
		Cartucho cartucho = dao.getOne(idCartucho);
		if (cartucho == null)
			throw new NegocioException("Cartucho inválido.");
		int estoque = cartucho.getQuantidade();
		if (operacao.equals(Operacao.ENTRADA)) {
			estoque += quantidade;
		} else {
			estoque -= quantidade;
		}
		if (estoque < 0)
			throw new NegocioException("Estoque negativo.");
		cartucho.setQuantidade(estoque);
		return dao.save(cartucho);
	}
	
	@Transactional
	public void deleteCartucho(Long id) {
		dao.deleteById(id);
	}
	
	private void validaDescricao(Cartucho cartucho) {
		Cartucho cartuchoDB =  dao.findByDescricao(cartucho.getDescricao());
		if (cartuchoDB != null && (cartucho.getId() == null || !cartucho.equals(cartuchoDB)))
				throw new NegocioException("Descrição já utilizada.");
	}
}
