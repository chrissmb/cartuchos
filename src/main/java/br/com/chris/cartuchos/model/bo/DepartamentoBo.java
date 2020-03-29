package br.com.chris.cartuchos.model.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.chris.cartuchos.model.bean.Departamento;
import br.com.chris.cartuchos.model.dao.DepartamentoDao;

@Component
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
	
	public Departamento addDepartamento(Departamento departamento) {
		departamento.setId(null);
		validaDescricao(departamento);
		return dao.save(departamento);
	}

	public Departamento updateDepartamento(Departamento departamento, Long id) {
		if (id == 1)
			throw new RuntimeException("Não é permitido alterar o departamento admin.");
		departamento.setId(id);
		validaDescricao(departamento);
		return dao.save(departamento);
	}
	
	public void deleteDepartamento(@PathVariable Long id) {
		if (id == 1)
			throw new RuntimeException("Não é permitido excluir o departamento admin.");
		dao.deleteById(id);
	}
	
	private void validaDescricao(Departamento departamento) {
		Departamento departamentoDB =  dao.findByDescricao(departamento.getDescricao());
		if (departamentoDB != null && (departamento.getId() == null || !departamento.equals(departamentoDB)))
				throw new RuntimeException("Descrição já utilizada.");
	}
}
