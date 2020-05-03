package com.desafio.digitounico.converters;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.desafio.digitounico.dto.DigitoUnicoDTO;
import com.desafio.digitounico.dto.ParametrosDigitoDTO;
import com.desafio.digitounico.entities.DigitoUnico;
import com.desafio.digitounico.entities.Usuario;
import com.desafio.digitounico.repositories.UsuarioRepository;

public class DigitoUnicoConverter implements Converter<DigitoUnico, DigitoUnicoDTO> {

	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Override
	public DigitoUnico convertToEntity(DigitoUnicoDTO dto) {
		DigitoUnico entity = new DigitoUnico();
		BeanUtils.copyProperties(dto, entity);
		Usuario usuario = usuarioRepository.getOne(dto.getIdUsuario());
		entity.setUsuario(usuario);
		return entity;
	}
	
	@Override
	public DigitoUnicoDTO convertToDTO(DigitoUnico entity) {
		DigitoUnicoDTO dto = new DigitoUnicoDTO();
		BeanUtils.copyProperties(entity, dto);
		dto.setIdUsuario(entity.getUsuario().getId());
		return dto;
	}
	
	public DigitoUnico convertParamToEntity(ParametrosDigitoDTO param, Integer digitoGerado) {
		DigitoUnico entity = new DigitoUnico();
		entity.setId(null);
		entity.setDigitoParam(param.getDigitoParam());
		entity.setConcatenacao(param.getConcatenacao());
		entity.setDigitoGerado(digitoGerado);
		Usuario usuario = usuarioRepository.getOne(param.getIdUsuario());
		entity.setUsuario(usuario);
		return entity;
	}
	
	public DigitoUnicoDTO convertParamToDTO(ParametrosDigitoDTO param, Integer digitoGerado) {
		DigitoUnicoDTO dto = new DigitoUnicoDTO();
		dto.setId(null);
		dto.setDigitoParam(param.getDigitoParam());
		dto.setConcatenacao(param.getConcatenacao());
		dto.setDigitoGerado(digitoGerado);
		dto.setIdUsuario(param.getIdUsuario());
		return dto;
	}
	
}
