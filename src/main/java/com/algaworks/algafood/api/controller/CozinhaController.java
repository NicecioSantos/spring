package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

	@Autowired
	private CadastroCozinhaService cozinhaService;

	@GetMapping
	public List<Cozinha> lista() {
		return cozinhaService.lista();
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> busca(@PathVariable Long id) {
		
		try {
			Cozinha cozinha = cozinhaService.busca(id);
			return ResponseEntity.status(HttpStatus.OK).body(cozinha);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}		
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cozinha adiciona(@RequestBody Cozinha cozinha) {
		return cozinhaService.adiciona(cozinha);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> actualiza(@RequestBody Cozinha novaCozinha, @PathVariable Long id) {
		
		try {
			Cozinha cozinhaExistente = cozinhaService.busca(id);
			BeanUtils.copyProperties(novaCozinha, cozinhaExistente, "id");
			cozinhaExistente = cozinhaService.adiciona(cozinhaExistente);
			return ResponseEntity.ok(cozinhaExistente);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}		
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> remove(@PathVariable Long id) {

		try {
			cozinhaService.exclui(id);
			return ResponseEntity.status(HttpStatus.ACCEPTED).build();

		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
			
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
}
