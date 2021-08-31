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
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.service.CadastroEstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController {
	
	@Autowired
	private CadastroEstadoService estadoService;
	
	@GetMapping
	public List<Estado> lista() {
		return estadoService.lista();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> busca(@PathVariable Long id) {
		try {
			 Estado estado = estadoService.busca(id);
			 return ResponseEntity.status(HttpStatus.OK).body(estado);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}		
	}
	
	@PostMapping
	public ResponseEntity<?> adiciona(@RequestBody Estado estado) {
		estado = estadoService.adiciona(estado);
		return ResponseEntity.ok(estado);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> actualiza(@PathVariable Long id, @RequestBody Estado estadoRequest) {
		try {
			estadoRequest.setId(id);
			Estado estado = estadoService.actualiza(estadoRequest);
			return ResponseEntity.status(HttpStatus.OK).body(estado);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> exclui(@PathVariable Long id) {
		try {
			estadoService.exclui(id);
			return ResponseEntity.status(HttpStatus.OK).build();
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}
}
