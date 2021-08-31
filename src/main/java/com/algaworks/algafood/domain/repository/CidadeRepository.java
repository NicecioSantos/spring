package com.algaworks.algafood.domain.repository;

import java.util.List;

import com.algaworks.algafood.domain.model.Cidade;

public interface CidadeRepository {
	
	List<Cidade> buscaTodas();	
	Cidade buscaPorId(Long id);
	Cidade insere(Cidade cidade);
	void remove(Cidade cidade);
}
