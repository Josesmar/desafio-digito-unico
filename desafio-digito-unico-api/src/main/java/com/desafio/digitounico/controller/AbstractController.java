package com.desafio.digitounico.controller;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.desafio.digitounico.dto.BaseDTO;
import com.desafio.digitounico.entities.Persistable;
import com.desafio.digitounico.services.AbstractService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractController<T extends Persistable<PK>, DTO extends BaseDTO, PK extends Serializable> {

	protected abstract AbstractService<T, DTO, PK> getService();
	
	@GetMapping(value = "/all")
	@ResponseStatus(code = HttpStatus.OK)
	@ApiOperation(httpMethod = "GET", 
		value = "Listar todos", 
		nickname = "getAll", 
		tags = { "default", })
	@ApiResponses(value = { 
		@ApiResponse(code = 200, message = "Resultado da busca"),
		@ApiResponse(code = 204, message = "Nenhum resultado foi encontrado!"),
		@ApiResponse(code = 400, message = "Erro!")})
	public List<DTO> getAll() {
		log.debug(">> getAll {}");
		List<DTO> entities = getService().findAll();
		log.debug("<< getAll [entities={}] ", entities);
		return entities;
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	@ApiOperation(httpMethod = "GET", 
		value = "Pesquisar por id", 
		nickname = "getById", 
		tags = { "default", })
	@ApiResponses(value = { 
		@ApiResponse(code = 200, message = "Encontrado com sucesso!"),
		@ApiResponse(code = 400, message = "O id pesquisado não existe!")})
	public DTO getById(@PathVariable PK id) {
		log.debug(">> getById {}", id);
		DTO entity = getService().findById(id);
		log.debug("<< getById {} {}", id, entity);
		return entity;
	}
	
	@PostMapping(value = "/")
	@ApiOperation(httpMethod = "POST", 
		value = "Criar novo", 
		nickname = "create", 
		tags = { "default", })
	@ApiResponses(value = { 
		@ApiResponse(code = 201, message = "Criado com sucesso!"),
		@ApiResponse(code = 400, message = "Falha ao criar")})
	public ResponseEntity<DTO> create(@Valid @RequestBody DTO entity){
		log.debug(" >> create entity {} ", entity);
		entity = getService().save(entity);
		log.debug(" << create entity {} ", entity);
		return new ResponseEntity<>(entity, HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/{id}")
	@ApiOperation(httpMethod = "PUT", 
		value = "Atualizar por id", 
		nickname = "update", 
		tags = { "default", })
	@ApiResponses(value = { 
		@ApiResponse(code = 201, message = "Atualizado com sucesso!"),
		@ApiResponse(code = 400, message = "Falha ao atualizar")})
	public ResponseEntity<DTO> update(@PathVariable(value = "id", required = true) PK id, @Valid @RequestBody DTO entity){
		log.debug(" >> create entity {} ", entity);
		entity = getService().update(id, entity);
		log.debug(" << create entity {} ", entity);
		return new ResponseEntity<>(entity, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation(httpMethod = "DELETE", 
		value = "Deletar por id", 
		nickname = "delete", 
		tags = { "default", })
	@ApiResponses(value = { 
		@ApiResponse(code = 200, message = "Deletado com sucesso!"),
		@ApiResponse(code = 400, message = "Falha ao deletar!")})
	public ResponseEntity<PK> deleteById(@PathVariable PK id) {
		log.debug(">> getById {}", id);
		getService().delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}

