package com.desafio.digitounico.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.ResponseEntity;

import com.desafio.digitounico.dto.ParametrosDigitoDTO;

public class ValidatorUtils {

	public static ResponseEntity<String> validarParametros(ParametrosDigitoDTO dto) {
		List<String> erros = listarErrosParam(dto);
		if (CollectionUtils.isNotEmpty(listarErrosParam(dto))) {
			String body = "";
			erros.forEach(body::concat);
			return ResponseEntity.badRequest().body(body);
		}
		
		return ResponseEntity.ok().body("OK");
	}

	public static List<String> listarErrosParam(ParametrosDigitoDTO dto) {
		List<String> list = new ArrayList<>();
		
		if (Objects.isNull(dto.getIdUsuario())) {
			list.add("O id usuário não pode ser nulo!");
		}
		
		if (Objects.isNull(dto.getDigitoParam())) {
			list.add("O dígito não pode ser nulo!");
		}
		
		if (Objects.isNull(dto.getConcatenacao())) {
			list.add("O nº de concatenações não pode ser nulo!");
		}
		
		return list;
	}

}
