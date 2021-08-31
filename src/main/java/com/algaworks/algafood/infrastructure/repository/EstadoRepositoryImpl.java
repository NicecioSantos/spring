package com.algaworks.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Component
public class EstadoRepositoryImpl implements EstadoRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Estado> buscaTodos() {
		return entityManager.createQuery("from Estado", Estado.class).getResultList();
	}

	@Override
	public Estado buscaPorId(Long id) {
		try {
			return entityManager.find(Estado.class, id);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("Impossivel actualizar para o estado %d. Não existe", id));
		}
	}

	@Override
	@Transactional
	public Estado insere(Estado estado) {
		return entityManager.merge(estado);
	}

	@Override
	@Transactional
	public void remove(Estado estado) {
		entityManager.remove(estado);
	}
}
