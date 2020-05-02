package com.desafio.digitounico.services;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.desafio.digitounico.converters.Converter;
import com.desafio.digitounico.converters.UsuarioConverter;
import com.desafio.digitounico.dto.UsuarioDTO;
import com.desafio.digitounico.entities.Usuario;
import com.desafio.digitounico.repositories.UsuarioRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class UsuarioService extends AbstractService<Usuario, UsuarioDTO, Long> {
	
	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private UsuarioConverter converter;
	
	@Override
	protected JpaRepository<Usuario, Long> getRepository() {
		return repository;
	}

	@Override
	protected Converter<Usuario, UsuarioDTO> getConverter() {
		return this.converter;
	}
	
	/**
	 * Verificar se o dígito único é valido
	 * 
	 * @param usuario objeto utilizado para filtrar
	 * @return true se for válido, false se for inválido
	 */
	public boolean isValid(UsuarioDTO usuario) {
		if (Objects.isNull(usuario)) {
			return Boolean.FALSE;
		}
		log.debug(">> isValid [id={}] ", usuario.getId());
		boolean result = repository.isValid(usuario.getId());
		log.debug(">> isValid [id={}] ", usuario.getId());
		return result;
	}


}
