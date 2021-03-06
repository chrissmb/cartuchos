package br.com.chris.cartuchos.model.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Departamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Size(min=2,max=20)
	@Column(unique=true)
    @NotNull
	private String descricao;
	
    @NotNull
	private boolean ativo = true;
	
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
	
	public void setAtivo(boolean ativo) {
	    this.ativo = ativo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Departamento other = (Departamento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Departamento [id=" + id + ", descricao=" + descricao + ", ativo=" + ativo + "]";
	}
}