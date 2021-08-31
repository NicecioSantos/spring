package com.algaworks.algafood.domain.repository;

import java.util.List;

import com.algaworks.algafood.domain.model.Estado;

public interface EstadoRepository {
	
	List<Estado> buscaTodos();	
	Estado buscaPorId(Long id);
	Estado insere(Estado estado);
	void remove(Estado estado);
}
