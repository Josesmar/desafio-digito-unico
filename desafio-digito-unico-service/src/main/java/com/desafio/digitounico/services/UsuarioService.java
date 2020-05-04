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

	/**
	 * Verificar se o dígito único é valido
	 * 
	 * @param usuario objeto utilizado para filtrar
	 * @return true se for válido, false se for inválido
	 */
	public boolean validarUsuario(UsuarioDTO usuario) {
		if (Objects.isNull(usuario)) {
			return Boolean.FALSE;
		}
		log.debug(">> validarUsuario [id={}] ", usuario.getId());
		boolean result = repository.validarUsuario(usuario.getId());
		log.debug(">> validarUsuario [id={}] ", usuario.getId());
		return result;
	}

	public UsuarioDTO criptografar(Long id) {
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
