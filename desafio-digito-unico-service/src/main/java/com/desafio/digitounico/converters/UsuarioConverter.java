package com.desafio.digitounico.converters;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.desafio.digitounico.dto.UsuarioDTO;
import com.desafio.digitounico.entities.Usuario;

@Component
public class UsuarioConverter implements Converter<Usuario, UsuarioDTO> {
	
	@Override
	public Usuario convertToEntity(UsuarioDTO dto) {
		Usuario entity = new Usuario();
		BeanUtils.copyProperties(dto, entity);
		return entity;
	}
	
	@Override
	public UsuarioDTO convertToDTO(Usuario entity) {
		UsuarioDTO dto = new UsuarioDTO();
		BeanUtils.copyProperties(entity, dto);
		return dto;
	}

}
