package com.algaworks.algafood.api.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@RestController
@RequestMapping("/teste")
public class TesteController {

	@Autowired
	CozinhaRepository cozinhaRepository;

	@Autowired
	RestauranteRepository restauranteRepository;

	@GetMapping("/cozinhas/todas-por-nome")
	public List<Cozinha> todasPorNomeExacto(@RequestParam String nome) {
		return cozinhaRepository.findTodasByNome(nome);
	}

	@GetMapping("/cozinhas/todas-por-nome-like")
	public List<Cozinha> todasPorNomeLike(@RequestParam String nome) {
		return cozinhaRepository.findTodasByNomeContaining(nome);
	}

	@GetMapping("/cozinhas/uma-por-nome")
	public Optional<Cozinha> umaPorNome(@RequestParam String nome) {
		return cozinhaRepository.findByNome(nome);
	}

	@GetMapping("/restaurantes/taxa-frete-between")
	public List<Restaurante> porTaxaFreteBetween(@RequestParam BigDecimal taxaInicial, BigDecimal taxaFinal) {
		return restauranteRepository.findByTaxaFreteBetween(taxaInicial, taxaFinal);
	}
	
	@GetMapping("/restaurantes/taxa-frete-between-e-nome")
	public List<Restaurante> porTaxaFreteBetweenENomeCozinha(@RequestParam BigDecimal taxaInicial, BigDecimal taxaFinal, String nomeCozinha) {
		return restauranteRepository.findByTaxaFreteBetweenAndCozinhaNomeContaining(taxaInicial, taxaFinal, nomeCozinha);
	}
	
	@GetMapping("/restaurantes/taxa-frete-e-cozinha")
	public List<Restaurante> porTaxaFreteENomeCozinha(BigDecimal taxaInicial, BigDecimal taxaFinal, String nomeCozinha) {
		return restauranteRepository.buscaIntervaloFreteNaCozinha(taxaInicial, taxaFinal, nomeCozinha);
	}
	
	@GetMapping("/restaurantes/taxa-frete-pagamento")
	public List<Restaurante> porTaxaFretePagamento(BigDecimal taxaInicial, BigDecimal taxaFinal, String pagamento) {
		return restauranteRepository.buscaFreteTipoPagamento(taxaInicial, taxaFinal, pagamento);
	}
	
	@GetMapping("/restaurantes/exite-cozinha-nome")
	public boolean existeNomeCozinha(@RequestParam String nomeCozinha) {
		return restauranteRepository.existsByCozinhaNomeContaining(nomeCozinha);
	}
	
	@GetMapping("/restaurantes/conta-cozinha-id")
	public int contaCozinhaId(@RequestParam Long cozinhaId) {
		return restauranteRepository.countByCozinhaId(cozinhaId);
	}
	
	@GetMapping("/restaurantes/com-frete-gratis")
	public List<Restaurante> porFreteGratis(String nome) {
		
		/*var comFreteGratis = new RestauranteComFreteGratisSpec();
		var comNomeSemelhante = new RestauranteComNomeSemelhanteSpec(nome);*/
		
		return restauranteRepository.buscaComFreteGratis(nome);
	}
	
	@GetMapping("/restaurante/primeiro")
	public Optional<Restaurante> buscaPrimeiro() {
		return restauranteRepository.buscaPrimeiro();
	}
	
	@GetMapping("/cozinha/primeira")
	public Optional<Cozinha> buscaPrimeira() {
		return cozinhaRepository.buscaPrimeiro();
	}
}
