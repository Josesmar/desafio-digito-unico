package com.desafio.digitounico.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParametrosDigitoDTO {

	private Long idUsuario;
	private String digitoParam;
	private Integer concatenacao;

}
