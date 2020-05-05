package com.desafio.digitounico.controller;

import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.desafio.digitounico.converters.DigitoUnicoConverter;
import com.desafio.digitounico.dto.DigitoUnicoDTO;
import com.desafio.digitounico.dto.ParametrosDigitoDTO;
import com.desafio.digitounico.entities.DigitoUnico;
import com.desafio.digitounico.services.AbstractService;
import com.desafio.digitounico.services.DigitoUnicoService;
import com.desafio.digitounico.utils.CacheUtils;
import com.desafio.digitounico.utils.DigitoUtils;
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
	
	@Override
	protected AbstractService<DigitoUnico, DigitoUnicoDTO, Long> getService() {
		return this.service;
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Integer calcularDigitoUnico(@Valid @RequestBody ParametrosDigitoDTO dto) {
		ResponseEntity<String> response = ValidatorUtils.validarParametros(dto);
		if(response.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
			throw new ResponseStatusException(response.getStatusCode(), response.getBody());
		}
		log.debug(" >> calcularDigitoUnico [dto={}] ", dto);
		Integer digitoUnico = calcular(dto);
		salvarNovoDigito(dto, digitoUnico);
		log.debug(" << calcularDigitoUnico [dto={}, digitoUnico={}] ", dto, digitoUnico);
		
		return 1;
	}

	private Integer calcular(ParametrosDigitoDTO dto) {
		Integer digitoUnico = CacheUtils.buscar(dto.getDigitoParam(), dto.getConcatenacao());
		if (Objects.isNull(digitoUnico)) {
			StringBuilder digitoParam = new StringBuilder();
			for (int i = 0; i < dto.getConcatenacao(); i++) {
				digitoParam.append(dto.getDigitoParam());
				digitoUnico = DigitoUtils.somarDigitos(digitoParam.toString());
				CacheUtils.adicionar(dto.getDigitoParam(), dto.getConcatenacao(), digitoUnico);
			}
		}
		return digitoUnico;
	}

	public void salvarNovoDigito(ParametrosDigitoDTO paramDto, Integer digitoGerado) {
		DigitoUnicoDTO dto = converter.convertParamToDTO(paramDto, digitoGerado);
		if (service.validarDigito(dto)) {
			log.debug(" >> create entity [dto={}] ", dto);
			getService().save(dto);
			log.debug(" << create entity [dto={}, digitoGerado={}] ", paramDto, digitoGerado);
		}
	}
	
	

}
