package br.com.chris.cartuchos.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioDao extends JpaRepository<Usuario, Long> {
	
}
