package com.desafio.digitounico.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.desafio.digitounico.converters.DigitoUnicoConverter;
import com.desafio.digitounico.dto.DigitoUnicoDTO;
import com.desafio.digitounico.dto.ParametrosDigitoDTO;
import com.desafio.digitounico.entities.DigitoUnico;
import com.desafio.digitounico.services.AbstractService;
import com.desafio.digitounico.services.DigitoUnicoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Api("DigitoUnico")
@RequestMapping("/api/digitounico")
public class DigitoUnicoController extends AbstractController<DigitoUnico, DigitoUnicoDTO, Long> {
	
	@Autowired
	DigitoUnicoService service;
	
	@Autowired
	DigitoUnicoConverter converter;
	

	@Override
	protected AbstractService<DigitoUnico, DigitoUnicoDTO, Long> getService() {
		return this.service;
	}
	
	@GetMapping("/all/usuario/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	@ApiOperation(httpMethod = "GET", 
		value = "Listar todos os dígitos de um usuário específico", 
		nickname = "getAllByUsuario", 
		tags = { "digitounico", })
	@ApiResponses(value = { 
		@ApiResponse(code = 200, message = "Lista de dígitos"),
		@ApiResponse(code = 204, message = "Nenhum dígito encontrado!"),
		@ApiResponse(code = 400, message = "Erro!")})
	public List<DigitoUnicoDTO> getAllByUsuario (@PathVariable(value = "id") Long id) {
		log.debug(" >> findAllByUsuario [id={}] ", id);
		service.validarUsuario(id);
		List<DigitoUnicoDTO> list = service.getAllByUsuario(id);
		log.debug(" << findAllByUsuario [id={}] ", id);
		return list;
	}
	
	@PostMapping("/calcular")
	@ApiOperation(httpMethod = "POST", 
		value = "Criar um dígito", 
		nickname = "createDigito", 
		tags = { "digitounico", })
	@ApiResponses(value = { 
		@ApiResponse(code = 201, message = "Dígito criado com sucesso"),
		@ApiResponse(code = 400, message = "Falha ao criar o dígito")})
	public ResponseEntity<DigitoUnicoDTO> createDigito(@Valid @RequestBody ParametrosDigitoDTO dto) {
		log.debug(" >> createDigito [dto={}] ", dto);
		Integer digitoUnico = service.calcularDigitoUnico(dto);
		DigitoUnicoDTO dtoCriado = service.createDigito(dto, digitoUnico);
		log.debug(" << createDigito [dto={}] ", dto);
		return new ResponseEntity<>(dtoCriado, HttpStatus.CREATED);
	}
	
	@PostMapping("/calcular/usuario")
	@ApiOperation(httpMethod = "POST", 
		value = "Criar um dígito para um usuário específico", 
		nickname = "createDigitoByUsuario", 
		tags = { "digitounico", })
	@ApiResponses(value = { 
		@ApiResponse(code = 201, message = "Dígito criado com sucesso"),
		@ApiResponse(code = 400, message = "Falha ao criar o dígito")})
	public ResponseEntity<DigitoUnicoDTO> createDigitoByUsuario(@Valid @RequestBody ParametrosDigitoDTO dto) {
		log.debug(" >> createDigitoByUsuario [dto={}] ", dto);
		service.validarUsuario(dto.getIdUsuario());
		Integer digitoUnico = service.calcularDigitoUnico(dto);
		DigitoUnicoDTO dtoCriado = service.createDigito(dto, digitoUnico);
		log.debug(" << createDigitoByUsuario [dto={}] ", dto);
		return new ResponseEntity<>(dtoCriado, HttpStatus.CREATED);
	}
	
}
