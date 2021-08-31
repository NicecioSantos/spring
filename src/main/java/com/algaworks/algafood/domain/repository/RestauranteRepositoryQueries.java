package com.algaworks.algafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import com.algaworks.algafood.domain.model.Restaurante;

public interface RestauranteRepositoryQueries {
	
	List<Restaurante> buscaFreteTipoPagamento(BigDecimal taxaInicial, BigDecimal taxaFinal, 
			String tipoPagamento);
	
	List<Restaurante> buscaComFreteGratis(String nomeRestaurante);

}