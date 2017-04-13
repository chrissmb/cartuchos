package br.com.chris.cartuchos.model;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
public class Registro {
	@Id
	@GeneratedValue
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
}
