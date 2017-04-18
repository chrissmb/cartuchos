package br.com.chris.cartuchos.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Departamento {
    @Id
    @GeneratedValue
    private Long id;
    
    @Size(min=2,max=20)
	@Column(unique=true)
    @NotNull
	private String descricao;
	
	private boolean ativo;
	
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
	
	public boolean isAtivo() {
	    return ativo;
	}
	
	public void setAtivo() {
	    this.ativo = ativo;
	}
}