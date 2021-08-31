package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;

@Service
public class CadastroCozinhaService {

	@Autowired
	private CozinhaRepository cozinhaRepository;

	public List<Cozinha> lista() {
		return cozinhaRepository.findAll();
	}

	public Cozinha busca(Long id) {
		return cozinhaRepository.findById(id).orElseThrow(
				() -> new EntidadeNaoEncontradaException(String.format("Cozinha com ID %d não existe", id)));
	}

	public Cozinha adiciona(Cozinha cozinha) {
		return cozinhaRepository.save(cozinha);
	}

	public void exclui(Long id) {
		try {
			cozinhaRepository.deleteById(id);

		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(String.format("Cozinha com ID %d não existe", id));

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("Cozinha de ID %d está em uso, não pode ser Excluida.", id));
		}
	}
}
