package com.desafio.digitounico.converters;

import org.springframework.beans.BeanUtils;

import com.desafio.digitounico.dto.DigitoUnicoDTO;
import com.desafio.digitounico.entities.DigitoUnico;

public class DigitoUnicoConverter implements Converter<DigitoUnico, DigitoUnicoDTO> {

	@Override
	public DigitoUnico convertToEntity(DigitoUnicoDTO dto) {
		DigitoUnico entity = new DigitoUnico();
		BeanUtils.copyProperties(dto, entity);
		return entity;
	}
	
	@Override
	public DigitoUnicoDTO convertToDTO(DigitoUnico entity) {
		DigitoUnicoDTO dto = new DigitoUnicoDTO();
		BeanUtils.copyProperties(entity, dto);
		return dto;
	}
}
