package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroCidadeService {
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;

	public List<Cidade> lista() {		
		return cidadeRepository.buscaTodas();
	}

	public Cidade busca(Long id) {
		Cidade cidade = cidadeRepository.buscaPorId(id);
		
		if (cidade == null) {
			throw new EntidadeNaoEncontradaException(String.format("Cidade com ID %d não encontrada.", id));
		}
		
		return cidade;
	}

	public Cidade adiciona(Cidade cidade) {		
		return cidadeRepository.insere(cidade);
	}

	public Cidade actualiza(Cidade cidadeRequest) {
		Long cidadeId = cidadeRequest.getId();
		Cidade cidade = cidadeRepository.buscaPorId(cidadeId);
		
		if (cidade == null) {
			throw new EntidadeNaoEncontradaException(String.format("Cidade com ID %d não encontrada.", cidadeId));
		}
		
		Long estadoId = cidadeRequest.getEstado().getId();
		Estado estado = estadoRepository.buscaPorId(estadoId);
		
		if (estado == null) {
			throw new EntidadeEmUsoException(String.format("Impossivel actualizar para o estado %d. Não existe", estadoId));
		}
		
		cidadeRequest.setEstado(estado);
		
		BeanUtils.copyProperties(cidadeRequest, cidade, "id");
		return cidadeRepository.insere(cidade);
	}

	public void exclui(Long id) {
		Cidade cidade = cidadeRepository.buscaPorId(id);
		
		if (cidade == null) {
			throw new EntidadeNaoEncontradaException(String.format("Cidade com ID %d não encontrada.", id));
		}
		
		cidadeRepository.remove(cidade);		
	}

}
