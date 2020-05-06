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
public class DigitoUnicoDTO extends BaseDTO {

	private Long id;
	private String digitoParam;
	private Integer concatenacao;
	private Integer digitoGerado;
	private Long idUsuario;

}
