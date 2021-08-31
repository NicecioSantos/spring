package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroEstadoService {

	@Autowired
	private EstadoRepository estadoRepository;

	public List<Estado> lista() {
		return estadoRepository.buscaTodos();
	}

	public Estado busca(Long id) {
		Estado estado = estadoRepository.buscaPorId(id);

		if (estado == null) {
			throw new EntidadeNaoEncontradaException(String.format("Estado com ID %d não encontrado.", id));
		}

		return estado;
	}

	public Estado adiciona(Estado estado) {
		return estadoRepository.insere(estado);
	}

	public Estado actualiza(Estado estadoRequest) {
		Long estadoId = estadoRequest.getId();
		Estado estado = estadoRepository.buscaPorId(estadoId);

		if (estado == null) {
			throw new EntidadeNaoEncontradaException(String.format("Estado com ID %d não encontrado.", estadoId));
		}

		BeanUtils.copyProperties(estadoRequest, estado, "id");
		return estadoRepository.insere(estado);
	}

	public void exclui(Long id) {
		Estado estado = estadoRepository.buscaPorId(id);
		
		if (estado == null) {
			throw new EntidadeNaoEncontradaException(String.format("Estado com ID %d não encontrado.", id));
		}
		
		try {
			estadoRepository.remove(estado);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("Entidade com ID %d não pode ser excluida, está em uso.", id));
		}
	}
}
