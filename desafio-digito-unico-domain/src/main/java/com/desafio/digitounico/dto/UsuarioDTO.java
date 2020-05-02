package com.desafio.digitounico.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO extends BaseDTO {

	private Long id;
	private String nome;
	private String email;
	private List<DigitoUnicoDTO> digitos = new ArrayList<>();

}
