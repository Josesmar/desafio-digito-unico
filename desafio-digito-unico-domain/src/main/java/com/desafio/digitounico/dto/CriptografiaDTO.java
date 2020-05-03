package com.desafio.digitounico.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CriptografiaDTO extends BaseDTO {

	private String chavePublica;
	private String chavePrivate;
	
}
