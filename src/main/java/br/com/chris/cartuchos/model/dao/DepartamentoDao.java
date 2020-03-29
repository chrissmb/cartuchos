package br.com.chris.cartuchos.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.chris.cartuchos.model.bean.Departamento;

public interface DepartamentoDao extends JpaRepository<Departamento, Long> {
	
	Departamento findByDescricao(String descricao);
	List<Departamento> findByAtivo(Boolean ativo);
}
