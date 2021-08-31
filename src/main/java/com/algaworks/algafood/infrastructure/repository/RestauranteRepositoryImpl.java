package com.algaworks.algafood.infrastructure.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepositoryQueries;
import com.algaworks.algafood.infrastructure.spec.RestauranteSpecs;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	@Lazy
	private RestauranteRepository restauranteRepository;
	
	//Usando JPQL
	/*@Override
	public List<Restaurante> buscaFreteTipoPagamento(BigDecimal taxaInicial, BigDecimal taxaFinal, 
			String tipoPagamento) {
		
		var jpql = new StringBuilder();
		jpql.append("from Restaurante where 1 = 1 ");
		
		var parametros = new HashMap<String, Object>();
		
		if (StringUtils.hasLength(tipoPagamento)) {
			jpql.append("and formaPagamento.descricao like :tipoPagamento ");
			parametros.put("tipoPagamento", "%" + tipoPagamento + "%");
		}
		
		if (taxaInicial != null) {
			jpql.append("and taxaFrete >= :taxaIni ");
			parametros.put("taxaIni", taxaInicial);
		}
		
		if (taxaFinal != null) {
			jpql.append("and taxaFrete <= :taxaFim");
			parametros.put("taxaFim", taxaFinal);
		}
		
		TypedQuery<Restaurante> query = entityManager.createQuery(jpql.toString(), Restaurante.class);
		
		parametros.forEach(
				(nomeParametro, valorParametro) -> query.setParameter(nomeParametro, valorParametro)
			);
		
		return query.getResultList();
	}
	*/
	
	//Usando API Criteria
	@Override
	public List<Restaurante> buscaFreteTipoPagamento(BigDecimal taxaInicial, BigDecimal taxaFinal, 
			String tipoPagamento) {
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Restaurante> criteriaQuery = criteriaBuilder.createQuery(Restaurante.class);
		
		Root<Restaurante> root = criteriaQuery.from(Restaurante.class);
		List<Predicate> predicados = new ArrayList<>();
		
		if (StringUtils.hasText(tipoPagamento)) {
			predicados.add(criteriaBuilder.like(root.get("nome"), "%" + tipoPagamento + "%"));
		}
		
		if (taxaInicial != null) {
			predicados.add(criteriaBuilder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaInicial));
		}
		
		if (taxaFinal != null) {
			predicados.add(criteriaBuilder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFinal));
		}
		
		criteriaQuery.where(predicados.toArray(new Predicate[0]));
		
		TypedQuery<Restaurante> query = entityManager.createQuery(criteriaQuery);
		return query.getResultList();		
	}

	@Override
	public List<Restaurante> buscaComFreteGratis(String nomeRestaurante) {
		return restauranteRepository.findAll(
				RestauranteSpecs.comFreteteGratis()
				.and(RestauranteSpecs.comNomeSemelhante(nomeRestaurante))
			);
	}

}
