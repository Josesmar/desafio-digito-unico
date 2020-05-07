package com.desafio.digitounico.converters;

import java.util.Objects;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.desafio.digitounico.dto.DigitoUnicoDTO;
import com.desafio.digitounico.entities.DigitoUnico;
import com.desafio.digitounico.entities.Usuario;
import com.desafio.digitounico.repositories.UsuarioRepository;

@Component
public class DigitoUnicoConverter implements Converter<DigitoUnico, DigitoUnicoDTO> {

	@Autowired
	UsuarioRepository usuarioRepository;

	@Override
	public DigitoUnico convertToEntity(DigitoUnicoDTO dto) {
		DigitoUnico entity = new DigitoUnico();
		BeanUtils.copyProperties(dto, entity);
		if (Objects.nonNull(dto.getIdUsuario())) {
			Usuario usuario = usuarioRepository.getOne(dto.getIdUsuario());
			entity.setUsuario(usuario);
		}
		return entity;
	}

	@Override
	public DigitoUnicoDTO convertToDTO(DigitoUnico entity) {
		DigitoUnicoDTO dto = new DigitoUnicoDTO();
		BeanUtils.copyProperties(entity, dto);
		if (Objects.nonNull(entity.getUsuario())) {
			dto.setIdUsuario(entity.getUsuario().getId());
		}
		return dto;
	}

}
