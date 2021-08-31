package com.algaworks.algafood.api.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.service.CadastroCidadeService;

@RestController
@RequestMapping("/cidades")
public class CidadeController {
	
	@Autowired
	private CadastroCidadeService cidadeService;

	@GetMapping
	public List<Cidade> lista() {
		return cidadeService.lista();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> busca(@PathVariable Long id) {
		try {
			Cidade cidade = cidadeService.busca(id);
			return ResponseEntity.status(HttpStatus.OK).body(cidade);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}		
	}
	
	@PostMapping
	public ResponseEntity<Cidade> adiciona(@RequestBody Cidade cidade) {
		cidade = cidadeService.adiciona(cidade);
		return ResponseEntity.status(HttpStatus.CREATED).body(cidade);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> actualiza(@PathVariable Long id, @RequestBody Cidade cidadeRequest) {
		try {
			cidadeRequest.setId(id);
			Cidade cidade = cidadeService.actualiza(cidadeRequest);
			return ResponseEntity.status(HttpStatus.OK).body(cidade);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}	
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> exclui(@PathVariable Long id) {
		try {
			cidadeService.exclui(id);
			return ResponseEntity.status(HttpStatus.ACCEPTED).build();
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}			
	}

}
