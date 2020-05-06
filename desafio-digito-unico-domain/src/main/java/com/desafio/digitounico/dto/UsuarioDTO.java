package com.desafio.digitounico.dto;

import java.util.ArrayList;
import java.util.List;

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
public class UsuarioDTO extends BaseDTO {

	private Long id;
	private String nome;
	private String email;
	@Builder.Default
	private List<DigitoUnicoDTO> digitos = new ArrayList<>();

}
