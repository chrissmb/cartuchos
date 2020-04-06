package br.com.chris.cartuchos.model.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.chris.cartuchos.model.NegocioException;
import br.com.chris.cartuchos.model.bean.Departamento;
import br.com.chris.cartuchos.model.dao.DepartamentoDao;

@Service
public class DepartamentoBo {

	@Autowired
	private DepartamentoDao dao;
	
	public List<Departamento> getDepartamentosAtivos() {
		return dao.findByAtivo(true);
	}
	public List<Departamento> getDepartamentos() {
		return dao.findAll();
	}
	
	public Departamento getDepartamento(Long id) {
		return dao.findById(id).get();
	}
	
	public Departamento getDepartamentoByDescricao(String descricao) {
		return dao.findByDescricao(descricao);
	}
	
	@Transactional
	public Departamento addDepartamento(Departamento departamento) {
		departamento.setId(null);
		validaDescricao(departamento);
		return dao.save(departamento);
	}
	
	@Transactional
	public Departamento updateDepartamento(Departamento departamento, Long id) {
		if (id == 1)
			throw new NegocioException("Departamento inválido.");
		departamento.setId(id);
		validaDescricao(departamento);
		return dao.save(departamento);
	}
	
	@Transactional
	public void deleteDepartamento(@PathVariable Long id) {
		if (id == 1)
			throw new NegocioException("Departamento inválido.");
		dao.deleteById(id);
	}
	
	private void validaDescricao(Departamento departamento) {
		Departamento departamentoDB =  dao.findByDescricao(departamento.getDescricao());
		if (departamentoDB != null && (departamento.getId() == null || !departamento.equals(departamentoDB)))
				throw new NegocioException("Descrição já utilizada.");
	}
}
