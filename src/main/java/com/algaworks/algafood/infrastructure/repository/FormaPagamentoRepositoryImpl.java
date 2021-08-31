package com.algaworks.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;

@Component
public class FormaPagamentoRepositoryImpl implements FormaPagamentoRepository {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<FormaPagamento> buscaTodas() {
		return entityManager.createQuery("from FormaPagamento", FormaPagamento.class).getResultList();
	}

	@Override
	public FormaPagamento buscaPorId(Long id) {
		return entityManager.find(FormaPagamento.class, id);
	}

	@Override
	public FormaPagamento insere(FormaPagamento formaPagamento) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(FormaPagamento formaPagamento) {
		// TODO Auto-generated method stub

	}

}
