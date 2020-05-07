package com.desafio.digitounico.services;

import java.security.GeneralSecurityException;
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
import com.desafio.digitounico.utils.CriptografiaUtils;

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

	public boolean validarUsuario(Long id) {
		if (Objects.isNull(id)) {
			return Boolean.FALSE;
		}
		log.debug(">> validarUsuario [id={}] ", id);
		boolean result = repository.validarUsuario(id) > 0;
		log.debug(">> validarUsuario [id={}] ", id);
		return result;
	}

	public UsuarioDTO criptografar(Long id) throws GeneralSecurityException {
		log.debug(">> criptografar [id={}] ", id);
		Usuario usuario = getRepository().findById(id).orElse(null);
		Usuario usuarioCriptografado = getRepository().save(CriptografiaUtils.criptografar(usuario));
		UsuarioDTO dto = converter.convertToDTO(usuarioCriptografado);
		log.debug(">> criptografar [id={}] ", id);
		return dto;
	}

	public UsuarioDTO descriptografar(Long id) {
		log.debug(">> descriptografar [id={}] ", id);
		Usuario usuarioCriptografado = getRepository().findById(id).orElse(null);
		Usuario usuario = getRepository().save(CriptografiaUtils.descriptografar(usuarioCriptografado));
		UsuarioDTO dto = converter.convertToDTO(usuario);
		log.debug("<< descriptografar [id={}] ", id);
		return dto;
	}

}
