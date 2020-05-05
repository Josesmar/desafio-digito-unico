package com.desafio.digitounico.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.desafio.digitounico.converters.DigitoUnicoConverter;
import com.desafio.digitounico.dto.DigitoUnicoDTO;
import com.desafio.digitounico.dto.ParametrosDigitoDTO;
import com.desafio.digitounico.entities.DigitoUnico;
import com.desafio.digitounico.services.AbstractService;
import com.desafio.digitounico.services.DigitoUnicoService;
import com.desafio.digitounico.utils.CacheUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
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
	
	@GetMapping("/findAllByUsuario/{id}")
	public List<DigitoUnicoDTO> findAllByUsuario (@PathVariable(value = "id") Long id) {
		log.debug(" >> findAllByUsuario [id={}] ", id);
		service.validarUsuario(id);
		List<DigitoUnicoDTO> list = service.findAllByUsuario(id);
		log.debug(" << findAllByUsuario [id={}] ", id);
		
		return list;
	}
	
	@PostMapping("/")
	public ResponseEntity<DigitoUnicoDTO> createDigito(@Valid ParametrosDigitoDTO dto) {
		log.debug(" >> createDigito [dto={}] ", dto);
		Integer digitoUnico = service.calcularDigitoUnico(dto);
		DigitoUnicoDTO dtoCriado = service.createDigito(dto, digitoUnico);
		log.debug(" << createDigitoByUsuario [dto={}] ", dto);
		return new ResponseEntity<>(dtoCriado, HttpStatus.CREATED);
	}
	
	@PostMapping("/usuario")
	public ResponseEntity<DigitoUnicoDTO> createDigitoByUsuario(@Valid ParametrosDigitoDTO dto) {
		log.debug(" >> createDigitoByUsuario [dto={}] ", dto);
		service.validarUsuario(dto.getIdUsuario());
		Integer digitoUnico = service.calcularDigitoUnico(dto);
		DigitoUnicoDTO dtoCriado = service.createDigito(dto, digitoUnico);
		log.debug(" << createDigitoByUsuario [dto={}] ", dto);
		return new ResponseEntity<>(dtoCriado, HttpStatus.CREATED);
	}
	
	@GetMapping("/cache")
	public Set<Map.Entry<String, Integer>> cache(){
        return CacheUtils.getCache();
    }
	
}
