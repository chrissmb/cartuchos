package br.com.chris.cartuchos.model;

import java.util.Calendar;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistroDao extends JpaRepository<Registro, Long> {
	
	List<Registro> findByDataBetween(Calendar inicio, Calendar fim);
}
