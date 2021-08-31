package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;

@Service
public class CadastroFormaPagamentoService {

	@Autowired
	private FormaPagamentoRepository repositorio;

	public FormaPagamento buscaPorId(Long id) {
		FormaPagamento formaPagamento = repositorio.buscaPorId(id);

		if (formaPagamento == null) {
			throw new EntidadeNaoEncontradaException(String.format("Forma de Pagamento com ID %d não encontrada", id));
		}
		
		return formaPagamento;
	}

}
