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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.desafio.digitounico.converters.DigitoUnicoConverter;
import com.desafio.digitounico.dto.DigitoUnicoDTO;
import com.desafio.digitounico.dto.ParametrosDigitoDTO;
import com.desafio.digitounico.entities.DigitoUnico;
import com.desafio.digitounico.services.AbstractService;
import com.desafio.digitounico.services.DigitoUnicoService;
import com.desafio.digitounico.services.UsuarioService;
import com.desafio.digitounico.utils.CacheUtils;
import com.desafio.digitounico.utils.ValidatorUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/digitounico")
public class DigitoUnicoController extends AbstractController<DigitoUnico, DigitoUnicoDTO, Long> {
	
	@Autowired
	DigitoUnicoService service;
	
	@Autowired
	DigitoUnicoConverter converter;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Override
	protected AbstractService<DigitoUnico, DigitoUnicoDTO, Long> getService() {
		return this.service;
	}
	
	@PostMapping("/")
	public ResponseEntity<DigitoUnicoDTO> createDigito(@Valid ParametrosDigitoDTO dto) {
		log.debug(" >> createDigito [dto={}] ", dto);
		Integer digitoUnico = calcularDigitoUnico(dto);
		DigitoUnicoDTO dtoCriado = service.createDigito(dto, digitoUnico);
		log.debug(" << createDigitoByUsuario [dto={}] ", dto);
		return new ResponseEntity<>(dtoCriado, HttpStatus.CREATED);
	}
	
	@PostMapping("/usuario/{id}")
	public ResponseEntity<DigitoUnicoDTO> createDigitoByUsuario(@Valid DigitoUnicoDTO dto) {
		log.debug(" >> createDigitoByUsuario [dto={}] ", dto);
		validarUsuario(dto.getIdUsuario());
		DigitoUnicoDTO dtoCriado = service.createDigitoByUsuario(dto);
		log.debug(" << createDigitoByUsuario [dto={}] ", dto);
		return new ResponseEntity<>(dtoCriado, HttpStatus.CREATED);
	}

	private void validarUsuario(Long idUsuario) {
		boolean usuarioExists = usuarioService.validarUsuario(idUsuario);
		if (!usuarioExists) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O usuário não existe!");
		}
	}
	
	public Integer calcularDigitoUnico(@Valid @RequestBody ParametrosDigitoDTO dto) {
		ResponseEntity<String> response = ValidatorUtils.validarParametros(dto);
		if(response.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
			throw new ResponseStatusException(response.getStatusCode(), response.getBody());
		}
		log.debug(" >> calcularDigitoUnico [dto={}] ", dto);
		Integer digitoUnico = service.calcularDigitoUnico(dto);
		log.debug(" << calcularDigitoUnico [dto={}, digitoUnico={}] ", dto, digitoUnico);
		
		return digitoUnico;
	}
	
	@GetMapping("/cache")
	public Set<Map.Entry<String, Integer>> cache(){
        return CacheUtils.getCache();
    }

	@GetMapping("/findAllByUsuario/{id}")
	public List<DigitoUnicoDTO> findAllByUsuario (@PathVariable(value = "id") Long id) {
		log.debug(" >> findAllByUsuario [id={}] ", id);
		validarUsuario(id);
		List<DigitoUnicoDTO> list = service.findAllByUsuario(id);
		log.debug(" << findAllByUsuario [id={}] ", id);
		
		return list;
		
	}

}
