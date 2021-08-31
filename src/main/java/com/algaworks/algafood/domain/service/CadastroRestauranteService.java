package com.algaworks.algafood.domain.service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CadastroRestauranteService {

	@Autowired
	private RestauranteRepository repositorio;

	@Autowired
	private CadastroCozinhaService cozinhaService;

	@Autowired
	private CadastroFormaPagamentoService pagamentoService;

	public Restaurante buscaPorId(Long id) {
		Restaurante restaurante = repositorio.getById(id);		

		if (restaurante == null) {
			throw new EntidadeNaoEncontradaException(String.format("Restaurante com ID %d não encontrado", id));
		}

		return restaurante;
	}

	public List<Restaurante> lista() {
		return repositorio.findAll();
	}

	public Restaurante adiciona(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		Cozinha cozinha = cozinhaService.busca(cozinhaId);

		//Long formaPagamentoId = restaurante.getFormaPagamento().getId();
		//FormaPagamento formaPagamento = pagamentoService.buscaPorId(formaPagamentoId);

		/*if (cozinha == null || formaPagamento == null) {
			throw new EntidadeNaoEncontradaException(
					String.format("Cozinha com ID %d e/ou Forma de Pagamento com ID %d, não cadastrado em sistema.",
							cozinhaId, formaPagamentoId));
		}

		restaurante.setCozinha(cozinha);*/
		//restaurante.setFormaPagamento(formaPagamento);

		return repositorio.save(restaurante);
	}

	public Restaurante actualiza(Restaurante restauranteReq) {
		Long restauranteId = restauranteReq.getId();
		Restaurante restaurante = repositorio.getById(restauranteId);

		if (restaurante == null) {
			throw new EntidadeNaoEncontradaException(
					String.format("Restaurante com ID %d, não encontrado.", restauranteId));
		}
		
		BeanUtils.copyProperties(restauranteReq, restaurante, "id");
		restaurante = adiciona(restaurante);
		return restaurante;
	}

	public Restaurante actualizaParcialmente(Map<String, Object> campos, Long id) {
		Restaurante restaurante = repositorio.getById(id);
		
		if (restaurante == null) {
			throw new EntidadeNaoEncontradaException(
					String.format("Restaurante com ID %d, não encontrado.", id));
		}
		
		merge(campos, restaurante);
		
		return actualiza(restaurante);
		
	}

	private void merge(Map<String, Object> campos, Restaurante restaurante) {
		
		ObjectMapper objectMapper = new ObjectMapper();
		Restaurante restauranteMap = objectMapper.convertValue(campos, Restaurante.class);
		
		campos.forEach((chave, valor) -> {		
			
			Field field = ReflectionUtils.findField(Restaurante.class, chave);
			field.setAccessible(true);
			
			Object valorMap = ReflectionUtils.getField(field, restauranteMap);
			
			ReflectionUtils.setField(field, restaurante, valorMap);
		});
	}
}
