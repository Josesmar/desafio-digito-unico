package com.desafio.digitounico.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.desafio.digitounico.converters.UsuarioConverter;
import com.desafio.digitounico.dto.UsuarioDTO;
import com.desafio.digitounico.entities.Usuario;
import com.desafio.digitounico.services.AbstractService;
import com.desafio.digitounico.services.UsuarioService;
import com.desafio.digitounico.utils.ValidatorUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/usuario")
public class UsuarioController extends AbstractController<Usuario, UsuarioDTO, Long> {

	@Autowired
	UsuarioService service;

	@Autowired
	UsuarioConverter converter;

	@Override
	protected AbstractService<Usuario, UsuarioDTO, Long> getService() {
		return this.service;
	}

	@PutMapping("/{id}/criptografar")
	public void criptografarDadosUsuario(@PathVariable(value = "id", required = true) Long id) {
		ResponseEntity<String> response = ValidatorUtils.validarCriptografiaUsuario(id, Boolean.TRUE);
		if (response.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
			throw new ResponseStatusException(response.getStatusCode(), response.getBody());
		}
		
		UsuarioDTO dto = new UsuarioDTO();
		log.debug(" >> criptografarDadosUsuario [dto={}] ", dto);
		dto = service.criptografar(id);
		log.debug(" << criptografarDadosUsuario [dto={}] ", dto);
	}
	
	@PutMapping("/{id}/criptografar")
	public void descriptografarDadosUsuario(@PathVariable(value = "id", required = true) Long id) {
		ResponseEntity<String> response = ValidatorUtils.validarCriptografiaUsuario(id, Boolean.FALSE);
		if (response.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
			throw new ResponseStatusException(response.getStatusCode(), response.getBody());
		}
		
		UsuarioDTO dto = new UsuarioDTO();
		log.debug(" >> descriptografarDadosUsuario [dto={}] ", dto);
		dto = service.descriptografar(id);
		log.debug(" << descriptografarDadosUsuario [dto={}] ", dto);
	}

}
