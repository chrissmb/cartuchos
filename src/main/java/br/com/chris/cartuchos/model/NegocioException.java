package br.com.chris.cartuchos.model;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class NegocioException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	private String mensagem;
	
	private String detalhes;

	public NegocioException(String mensagem) {
		super(mensagem);
		this.mensagem = mensagem;
		detalhes = getMessageException(this);
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getDetalhes() {
		return detalhes;
	}

	public void setDetalhes(String detalhes) {
		this.detalhes = detalhes;
	}
	
	private String getMessageException(Exception e) {
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		return errors.toString();
	}
	
	public Map<String, String> getErroMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("mensagem", mensagem);
		map.put("detalhes", detalhes);
		return map;
	}
}

