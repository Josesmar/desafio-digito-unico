package com.desafio.digitounico.controller;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
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

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractController<T extends Persistable<PK>, DTO extends BaseDTO, PK extends Serializable> {

	protected abstract AbstractService<T, DTO, PK> getService();
	
	@GetMapping(value = "/")
	public List<DTO> getAll() {
		log.debug(">> getAll {}");
		List<DTO> entities = getService().findAll();
		log.debug("<< getAll [entities={}] ", entities);
		return entities;
	}
	
	@GetMapping("/{id}")
	public DTO getById(@PathVariable PK id) {
		log.debug(">> getById {}", id);
		DTO entity = getService().findById(id);
		log.debug("<< getById {} {}", id, entity);
		return entity;
	}
	
	@PostMapping(value = "/")
	@ResponseStatus(code = HttpStatus.CREATED)
	public void create(@Valid @RequestBody DTO entity){
		log.debug(" >> create entity {} ", entity);
		entity = getService().save(entity);
		log.debug(" << create entity {} ", entity);
	}
	
	@PutMapping(value = "/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public void update(@PathVariable(value = "id", required = true) PK id, @Valid @RequestBody DTO entity){
		log.debug(" >> create entity {} ", entity);
		entity = getService().update(id, entity);
		log.debug(" << create entity {} ", entity);
	}
	
	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable PK id) {
		log.debug(">> getById {}", id);
		getService().delete(id);
	}
}

