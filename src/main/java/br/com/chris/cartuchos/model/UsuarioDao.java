package br.com.chris.cartuchos.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioDao extends JpaRepository<Usuario, Long> {
	
	Usuario findByUsername(String username);
}
