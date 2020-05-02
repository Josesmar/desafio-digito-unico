package com.desafio.digitounico.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DigitoUnicoDTO extends BaseDTO {

	private Long id;
	private String digitoParam;
	private Long concatenacao;
	private Long digitoGerado;
	private Long idUsuario;

}
