package com.algaworks.algafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Restaurante;

@Repository
public interface RestauranteRepository extends CustomJpaRepository<Restaurante, Long>, 
	RestauranteRepositoryQueries, JpaSpecificationExecutor<Restaurante> {
	
	/*List<Restaurante> buscaTodos();
	Restaurante buscaPorId(Long id);
	Restaurante insere(Restaurante restaurante);
	void remove(Restaurante restaurante);*/
	
	List<Restaurante> findByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);
	
	List<Restaurante> findByTaxaFreteBetweenAndCozinhaNomeContaining(BigDecimal taxaInicial, BigDecimal taxaFinal, String nomeCozinha);
	
	//@Query("from Restaurante where taxaFrete between :taxaInicial and :taxaFinal and cozinha.nome like %:nome%")
	List<Restaurante> buscaIntervaloFreteNaCozinha(BigDecimal taxaInicial, BigDecimal taxaFinal, @Param("nome") String nomeCozinha);
	
	boolean existsByCozinhaNomeContaining(String nomeCozinha);
	
	int countByCozinhaId(Long cozinhaId);	
}
