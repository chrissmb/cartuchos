package br.com.chris.cartuchos.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartamentoDao extends JpaRepository<Departamento, Long> {
	
	Departamento findByDescricao(String descricao);
}
