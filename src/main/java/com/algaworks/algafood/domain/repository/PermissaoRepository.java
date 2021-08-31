package com.algaworks.algafood.domain.repository;

import java.util.List;

import com.algaworks.algafood.domain.model.Permissao;

public interface PermissaoRepository {
	
	List<Permissao> buscaTodas();	
	Permissao buscaPorId(Long id);
	Permissao insere(Permissao permissao);
	void remove(Permissao permissao);
}