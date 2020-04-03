package br.com.chris.cartuchos.model.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.chris.cartuchos.model.Role;

@Entity
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	@Size(min = 3, max = 10)
	@NotNull
	private String login;
	
	@Size(max = 200)
	private String nome;
	
	@Transient
	private String senhaPlana;
	
	@Transient
	private String senhaConfirmar;
	
	@JsonIgnore
	@Size(min=60, max=60)
	@NotNull
	@Column(name = "senha_hash")
	private String senhaHash;
	
	@NotNull
	private boolean enabled = true;
	
	@Enumerated(EnumType.STRING)
	@NotNull
	private Role role;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenhaPlana() {
		return senhaPlana;
	}

	public void setSenhaPlana(String senhaPlana) {
		this.senhaPlana = senhaPlana;
	}

	public String getSenhaConfirmar() {
		return senhaConfirmar;
	}

	public void setSenhaConfirmar(String senhaConfirmar) {
		this.senhaConfirmar = senhaConfirmar;
	}

	public String getSenhaHash() {
		return senhaHash;
	}

	public void setSenhaHash(String senhaHash) {
		this.senhaHash = senhaHash;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
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
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("Usuario [id=%s, login=%s, enabled=%s, role=%s]", id, login, enabled, role);
	}
}
