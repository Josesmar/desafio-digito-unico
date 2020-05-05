package com.desafio.digitounico.services;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import com.desafio.digitounico.converters.Converter;
import com.desafio.digitounico.converters.DigitoUnicoConverter;
import com.desafio.digitounico.dto.DigitoUnicoDTO;
import com.desafio.digitounico.dto.ParametrosDigitoDTO;
import com.desafio.digitounico.entities.DigitoUnico;
import com.desafio.digitounico.repositories.DigitoUnicoRepository;
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

	@Override
	protected JpaRepository<DigitoUnico, Long> getRepository() {
		return repository;
	}

	@Override
	protected Converter<DigitoUnico, DigitoUnicoDTO> getConverter() {
		return this.converter;
	}

	/**
	 * Buscar lista de dígitos únicos por filtro de id de usuário
	 * 
	 * @param idUsuario id que filtra a pesquisa
	 * @return lista de dígitos únicos filtrada por id de usuário
	 */
	public List<DigitoUnicoDTO> findAllByUsuario(Long idUsuario) {
		List<DigitoUnico> entities = repository.findAllByUsuario(idUsuario);
		log.debug(">> findAllByUsuario [entities={}] ", entities);
		List<DigitoUnicoDTO> dtos = entities.parallelStream().map(entity -> converter.convertToDTO(entity))
				.collect(Collectors.toList());
		log.debug("<< findAllByUsuario [dtos={}] ", dtos);
		return dtos;
	}

	/**
	 * Verificar se o dígito único é valido
	 * 
	 * @param digitoUnico objeto utilizado para filtrar
	 * @return true se for válido, false se for inválido
	 */
	public boolean validarDigito(DigitoUnicoDTO digitoUnico) {
		if (Objects.isNull(digitoUnico)) {
			return Boolean.FALSE;
		}
		log.debug(">> validarDigito [id={}] ", digitoUnico.getId());
		boolean result = repository.validarDigito(digitoUnico.getId());
		log.debug(">> validarDigito [id={}] ", digitoUnico.getId());
		return result;
	}

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
		if (validarDigito(dto)) {
			log.debug(" >> create entity [dto={}] ", dto);
			getService().save(dto);
			log.debug(" << create entity [dto={}, digitoGerado={}] ", paramDto, digitoGerado);
		}
	}

}
