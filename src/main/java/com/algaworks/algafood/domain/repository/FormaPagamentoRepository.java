package com.algaworks.algafood.domain.repository;

import java.util.List;

import com.algaworks.algafood.domain.model.FormaPagamento;

public interface FormaPagamentoRepository {
	
	List<FormaPagamento> buscaTodas();	
	FormaPagamento buscaPorId(Long id);
	FormaPagamento insere(FormaPagamento pagamento);
	void remove(FormaPagamento pagamento);
}
