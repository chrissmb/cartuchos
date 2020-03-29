package br.com.chris.cartuchos.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.chris.cartuchos.model.bean.Usuario;

public interface UsuarioDao extends JpaRepository<Usuario, Long> {
	
	Usuario findByUsername(String username);
}
