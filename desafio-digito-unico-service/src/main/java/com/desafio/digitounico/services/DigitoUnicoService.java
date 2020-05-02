package com.desafio.digitounico.services;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.desafio.digitounico.converters.Converter;
import com.desafio.digitounico.converters.DigitoUnicoConverter;
import com.desafio.digitounico.dto.DigitoUnicoDTO;
import com.desafio.digitounico.entities.DigitoUnico;
import com.desafio.digitounico.repositories.DigitoUnicoRepository;

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
	public boolean isValid(DigitoUnicoDTO digitoUnico) {
		if (Objects.isNull(digitoUnico)) {
			return Boolean.FALSE;
		}
		log.debug(">> isValid [id={}] ", digitoUnico.getId());
		boolean result = repository.isValid(digitoUnico.getId());
		log.debug(">> isValid [id={}] ", digitoUnico.getId());
		return result;
	}

}
