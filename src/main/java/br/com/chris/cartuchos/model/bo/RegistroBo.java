package br.com.chris.cartuchos.model.bo;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.chris.cartuchos.model.NegocioException;
import br.com.chris.cartuchos.model.Operacao;
import br.com.chris.cartuchos.model.bean.Departamento;
import br.com.chris.cartuchos.model.bean.Registro;
import br.com.chris.cartuchos.model.dao.RegistroDao;

@Service
public class RegistroBo {

	@Autowired
	private RegistroDao dao;
	
	@Autowired
	private CartuchoBo cartuchoBo;
	
	@Autowired
	private UsuarioBo usuarioBo;
	
	public List<Registro> getAllRegistros() {
		return dao.findAll();
	}
	
	public Page<Registro> getAllRegistrosPageable(Pageable pageable, Calendar dataInicio, Calendar dataFim) {
		if (dataInicio != null && dataFim != null) {
			dataInicio.set(Calendar.HOUR_OF_DAY, 0);
			dataInicio.set(Calendar.MINUTE, 0);
			dataInicio.set(Calendar.SECOND, 0);
			dataFim.set(Calendar.HOUR_OF_DAY, 23);
			dataFim.set(Calendar.MINUTE, 59);
			dataFim.set(Calendar.SECOND, 59);
			return dao.findByDataBetween(dataInicio, dataFim, pageable);
		}
		return dao.findAll(pageable);
	}
	
	public Registro getRegistro(Long id) {
		return dao.findById(id).get();
	}
	
	@Transactional
	public Registro addRegistro(Registro registro) {
		if (registro.getCartucho() == null || registro.getOperacao() == null)
			throw new NegocioException("Preenchimento obrigatório para cartucho e operação.");
		
		if (registro.getOperacao().equals(Operacao.ENTRADA)) {
			registro.setDepartamento(new Departamento());
			registro.getDepartamento().setId(1L);
		} else if (registro.getDepartamento() == null || registro.getDepartamento().getId() == 1L) {
			throw new NegocioException("Departamento inválido.");
		}
		cartuchoBo.estoqueCartucho(registro.getCartucho().getId(), registro.getQuantidade(), registro.getOperacao());
		registro.setUsuario(usuarioBo.getLogado());
		registro.setData(Calendar.getInstance());
		return dao.save(registro);
	}
	
	@Transactional
	public void deleteRegistro(Long id) {
		dao.deleteById(id);
	}
}
