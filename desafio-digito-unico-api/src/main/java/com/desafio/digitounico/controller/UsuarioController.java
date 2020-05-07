package com.desafio.digitounico.controller;

import java.security.GeneralSecurityException;

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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Api("Usuario")
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
	@ApiOperation(httpMethod = "PUT", 
			value = "Criptografar os atributos do usuário.", 
			nickname = "criptografar", 
			tags = { "usuario", })
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Usuário criptografado com sucesso!"),
			@ApiResponse(code = 400, message = "Falha ao criptografar usuário") })
	public ResponseEntity<UsuarioDTO> criptografarDadosUsuario(@PathVariable(value = "id", required = true) Long id) throws GeneralSecurityException {
		ResponseEntity<String> response = ValidatorUtils.validarCriptografiaUsuario(id, Boolean.TRUE);
		if (response.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
			throw new ResponseStatusException(response.getStatusCode(), response.getBody());
		}

		UsuarioDTO dto = new UsuarioDTO();
		log.debug(" >> criptografarDadosUsuario [dto={}] ", dto);
		dto = service.criptografar(id);
		log.debug(" << criptografarDadosUsuario [dto={}] ", dto);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@PutMapping("/{id}/descriptografar")
	@ApiOperation(httpMethod = "PUT", 
			value = "Descriptografar os atributos do usuário.", 
			nickname = "descriptografar", 
			tags = { "usuario", })
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Usuário descriptografado com sucesso!"),
			@ApiResponse(code = 400, message = "Falha ao descriptografar usuário")})
	public ResponseEntity<UsuarioDTO> descriptografarDadosUsuario(
			@PathVariable(value = "id", required = true) Long id) {
		ResponseEntity<String> response = ValidatorUtils.validarCriptografiaUsuario(id, Boolean.FALSE);
		if (response.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
			throw new ResponseStatusException(response.getStatusCode(), response.getBody());
		}

		UsuarioDTO dto = new UsuarioDTO();
		log.debug(" >> descriptografarDadosUsuario [dto={}] ", dto);
		dto = service.descriptografar(id);
		log.debug(" << descriptografarDadosUsuario [dto={}] ", dto);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

}
