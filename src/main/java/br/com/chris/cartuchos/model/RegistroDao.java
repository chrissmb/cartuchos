package br.com.chris.cartuchos.model;

import java.util.Calendar;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistroDao extends JpaRepository<Registro, Long> {
	
	Page<Registro> findByDataBetween(Calendar inicio, Calendar fim, Pageable pageable);
}
