package br.com.chris.cartuchos.model.bean;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import br.com.chris.cartuchos.model.Operacao;

@Entity
public class Registro {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@ManyToOne
	private Cartucho cartucho;
	
	@NotNull
	@ManyToOne
	private Departamento departamento;
	
	@NotNull
	@Min(1)
	private int quantidade;
	
	@NotNull
	private Calendar data;
	
	@NotNull
	private Operacao operacao;
	
	@NotNull
	@ManyToOne
	private Usuario usuario;

	public Long getId() {
		return id;
	}

	public Cartucho getCartucho() {
		return cartucho;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public Calendar getData() {
		return data;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCartucho(Cartucho cartucho) {
		this.cartucho = cartucho;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public void setData(Calendar data) {
		this.data = data;
	}

	public Operacao getOperacao() {
		return operacao;
	}

	public void setOperacao(Operacao operacao) {
		this.operacao = operacao;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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
		Registro other = (Registro) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Registro [id=" + id + ", cartucho=" + cartucho + ", departamento=" + departamento + ", quantidade="
				+ quantidade + ", data=" + data + ", operacao=" + operacao + ", usuario=" + usuario + "]";
	}
}
