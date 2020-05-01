package com.desafio.digitounico.converters;

import org.springframework.beans.BeanUtils;

public interface Converter<T, DTO> {

	public T convertToEntity(DTO dto);
	
	public DTO convertToDTO(T entity);

	public default DTO convertToClone(DTO source, DTO target) {
		BeanUtils.copyProperties(source, target, "id");
		return target;
	}
	
}
