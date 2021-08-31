package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

	@Autowired
	private CadastroRestauranteService cadastroService;

	@GetMapping
	public ResponseEntity<List<Restaurante>> lista() {
		List<Restaurante> restaurantes = cadastroService.lista();
		return ResponseEntity.status(HttpStatus.OK).body(restaurantes);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Restaurante> busca(@PathVariable Long id) {
		try {
			Restaurante restaurante = cadastroService.buscaPorId(id);
			return ResponseEntity.status(HttpStatus.OK).body(restaurante);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping
	public ResponseEntity<?> adiciona(@RequestBody Restaurante restauranteRequest) {
		try {
			Restaurante restaurante = cadastroService.adiciona(restauranteRequest);
			return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	@PutMapping("/{id}")
	public ResponseEntity<?> actualiza(@RequestBody Restaurante restauranteReq, @PathVariable Long id) {
		try {
			restauranteReq.setId(id);
			Restaurante restaurante = cadastroService.actualiza(restauranteReq);
			return ResponseEntity.ok(restaurante);
		} catch (EntidadeNaoEncontradaException e) {
			if (e.getMessage().contains("Restaurante")) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
			}
		}
	}

	@PatchMapping("/{id}")
	public ResponseEntity<?> actualizaParcial(@RequestBody Map<String, Object> campos, @PathVariable Long id) {
		try {
			Restaurante restaurante = cadastroService.actualizaParcialmente(campos, id);
			return ResponseEntity.status(HttpStatus.OK).body(restaurante);
		} catch (EntidadeNaoEncontradaException e) {
			if (e.getMessage().contains("Restaurante")) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
			}
		}
	}
}
