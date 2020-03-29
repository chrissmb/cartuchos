package br.com.chris.cartuchos.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.chris.cartuchos.model.bean.Cartucho;

public interface CartuchoDao extends JpaRepository<Cartucho, Long> {
	
	Cartucho findByDescricao(String descricao);
	List<Cartucho> findByDescricaoContaining(String descricao);
	List<Cartucho> findByAtivoTrue();
}
