package br.com.chris.cartuchos.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Entity
public class Departamento {
    @Id
    @GeneratedValue
    private Long id;
    
    @Size(min=3,max=20)
	@Column(unique=true)
	private String descricao;
	
	private boolean habilitado;
	
	public Long getId() {
	    return id;
	}
	
	public void setId(Long id) {
	    this.id = id;
	}
	
	public String getDescricao() {
	    return descricao;
	}
	
	public void setDescricao(String descricao) {
	    this.descricao = descricao;
	}
	
	public boolean isHabilitado() {
	    return habilitado;
	}
	
	public void setHabilitado() {
	    this.habilitado = habilitado;
	}
}