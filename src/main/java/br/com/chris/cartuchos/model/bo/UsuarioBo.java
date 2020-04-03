package br.com.chris.cartuchos.model.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.chris.cartuchos.model.bean.Usuario;
import br.com.chris.cartuchos.model.dao.UsuarioDao;

@Service
public class UsuarioBo {

	@Autowired
	UsuarioDao dao;
	
	private static final int SENHA_TAMANHO_MIN = 6;
	private static final String MSG_SENHA_TAM_MIN = String.format(
			"Tamanho da senha dever ser no mínimo %d caracteres", SENHA_TAMANHO_MIN);
	
	public List<Usuario> getAllUsuario() {
		return dao.findAll();
	}
	
	public Usuario getUsuario(Long id) {
		return dao.findById(id).get();
	}
	
	public Usuario getLogado() {
		UserDetails usuario = this.getUsuarioLogado();
		return dao.findByLogin(usuario.getUsername());
	}
	
	@Transactional
	public Usuario alteraSenha(Usuario usuario) {
		if (usuario.getSenhaPlana().length() < SENHA_TAMANHO_MIN)
			throw new RuntimeException(MSG_SENHA_TAM_MIN);
		UserDetails usr = this.getUsuarioLogado();
		Usuario usuarioDB = dao.findByLogin(usr.getUsername());
		if(usuarioDB == null)
			throw new RuntimeException("Usuário inválido.");
		boolean eSenhaAtualValida = validaHash(usuario.getSenhaConfirmar(), usuarioDB.getSenhaHash());
		if (!eSenhaAtualValida)
			throw new RuntimeException("Senha inválida.");
		usuarioDB.setSenhaHash((hashSenha(usuario.getSenhaPlana())));
		return dao.save(usuarioDB);
	}
	
	@Transactional
	public Usuario addUsuario(Usuario usuario) {
		if (usuario.getSenhaPlana().length() < SENHA_TAMANHO_MIN)
			throw new RuntimeException(MSG_SENHA_TAM_MIN);
		usuario.setSenhaHash(hashSenha(usuario.getSenhaPlana()));
		return dao.save(usuario);
	}
	
	@Transactional
	public Usuario updateUsuario(Long id, Usuario usuario) {
		Usuario usuarioDB = dao.findById(id).get();
		usuario.setSenhaHash(usuarioDB.getSenhaHash());
		usuario.setId(id);
		return dao.save(usuario);
	}
	
	@Transactional
	public void deleteUsuario(Long id) {
		dao.deleteById(id);
	}
	
	private String hashSenha(String senha) {
		PasswordEncoder bCrypt = new BCryptPasswordEncoder();
		return bCrypt.encode(senha);
	}
	
	private boolean validaHash(String senha, String hash) {
		PasswordEncoder bCrypt = new BCryptPasswordEncoder();
		return bCrypt.matches(senha, hash);
	}
	
	private UserDetails getUsuarioLogado() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return (UserDetails) auth.getPrincipal();
	}
}
