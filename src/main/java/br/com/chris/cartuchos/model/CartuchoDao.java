package br.com.chris.cartuchos.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartuchoDao extends JpaRepository<Cartucho, Long> {
	
	List<Cartucho> findByDescricaoStartingWith(String descricao);
	List<Cartucho> findByAtivoTrue();
}
