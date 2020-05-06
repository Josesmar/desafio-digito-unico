package com.desafio.digitounico.services;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.desafio.digitounico.converters.Converter;
import com.desafio.digitounico.converters.DigitoUnicoConverter;
import com.desafio.digitounico.dto.DigitoUnicoDTO;
import com.desafio.digitounico.dto.ParametrosDigitoDTO;
import com.desafio.digitounico.entities.DigitoUnico;
import com.desafio.digitounico.repositories.DigitoUnicoRepository;
import com.desafio.digitounico.utils.CacheUtils;
import com.desafio.digitounico.utils.DigitoUtils;
import com.desafio.digitounico.utils.ValidatorUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class DigitoUnicoService extends AbstractService<DigitoUnico, DigitoUnicoDTO, Long> {

	@Autowired
	private DigitoUnicoRepository repository;

	@Autowired
	private DigitoUnicoConverter converter;
	
	@Autowired
	private UsuarioService usuarioService;

	@Override
	protected JpaRepository<DigitoUnico, Long> getRepository() {
		return repository;
	}

	@Override
	protected Converter<DigitoUnico, DigitoUnicoDTO> getConverter() {
		return this.converter;
	}

	public List<DigitoUnicoDTO> findAllByUsuario(Long idUsuario) {
		List<DigitoUnico> entities = repository.findAllByUsuario(idUsuario);
		log.debug(">> findAllByUsuario [entities={}] ", entities);
		List<DigitoUnicoDTO> dtos = entities.parallelStream().map(entity -> converter.convertToDTO(entity))
				.collect(Collectors.toList());
		log.debug("<< findAllByUsuario [dtos={}] ", dtos);
		return dtos;
	}

	public DigitoUnicoDTO createDigito(ParametrosDigitoDTO paramDto, Integer digitoUnico) {
		DigitoUnicoDTO dto = converter.convertParamToDTO(paramDto, digitoUnico);
		DigitoUnicoDTO dtoSalvo = new DigitoUnicoDTO();
		if (validarDigito(dto)) {
			log.debug(" >> createDigito [dto={}] ", dto);
			dtoSalvo = save(dto);
			log.debug(" << createDigito [dto={}] ", dto);
		}
		
		return dtoSalvo;
	}
	
	public Integer calcularDigitoUnico(ParametrosDigitoDTO dto) {
		ResponseEntity<String> response = ValidatorUtils.validarParametros(dto);
		if (response.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
			throw new ResponseStatusException(response.getStatusCode(), response.getBody());
		}
		log.debug(" >> calcularDigitoUnico [dto={}] ", dto);
		Integer digitoUnico = calcular(dto);
		log.debug(" << calcularDigitoUnico [dto={}, digitoUnico={}] ", dto, digitoUnico);

		return digitoUnico;
	}

	private Integer calcular(ParametrosDigitoDTO paramDto) {
		Integer digitoUnico = CacheUtils.buscar(paramDto.getDigitoParam(), paramDto.getConcatenacao());
		if (Objects.isNull(digitoUnico)) {
			StringBuilder digitoParam = new StringBuilder();
			for (int i = 0; i < paramDto.getConcatenacao(); i++) {
				digitoParam.append(paramDto.getDigitoParam());
				digitoUnico = DigitoUtils.somarDigitos(digitoParam.toString());
				CacheUtils.adicionar(paramDto.getDigitoParam(), paramDto.getConcatenacao(), digitoUnico);
			}
		}
		return digitoUnico;
	}

	public boolean validarDigito(DigitoUnicoDTO digitoUnico) {
		if (Objects.isNull(digitoUnico)) {
			return Boolean.FALSE;
		}
		log.debug(">> validarDigito [id={}] ", digitoUnico.getId());
		boolean result = repository.validarDigito(digitoUnico.getId());
		log.debug(">> validarDigito [id={}] ", digitoUnico.getId());
		return result;
	}
	
	public void validarUsuario(Long idUsuario) {
		boolean usuarioExists = usuarioService.validarUsuario(idUsuario);
		if (!usuarioExists) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O usuário não existe!");
		}
	}
}
