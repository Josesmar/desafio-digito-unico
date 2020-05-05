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
		if (CollectionUtils.isNotEmpty(erros)) {
			String body = "";
			erros.forEach(body::concat);
			return ResponseEntity.badRequest().body(body);
		}
		
		return ResponseEntity.ok().body("OK");
	}

	public static List<String> listarErrosParam(ParametrosDigitoDTO dto) {
		List<String> list = new ArrayList<>();
		
		if (Objects.isNull(dto.getDigitoParam())) {
			list.add("O dígito não pode ser nulo!");
		}
		
		return list;
	}

	public static ResponseEntity<String> validarCriptografiaUsuario(Long id, boolean isCriptografia) {
		List<String> erros = listarErrosCriptografia(id, isCriptografia);
		if (CollectionUtils.isNotEmpty(erros)) {
			String body = "";
			erros.forEach(body::concat);
			return ResponseEntity.badRequest().body(body);
		}
		
		return ResponseEntity.ok().body("OK");
	}

	private static List<String> listarErrosCriptografia(Long id, boolean isCriptografia) {
		List<String> list = new ArrayList<>();
		String operacao = isCriptografia ? "criptografado" : "descriptografado";
		if (Objects.isNull(id)) {
			list.add("O id usuário não pode ser nulo!");
		}
		
		if ((isCriptografia && !CriptografiaUtils.checarUsuario(id)) || (!isCriptografia && CriptografiaUtils.checarUsuario(id))) {
			list.add("O usuário já foi ".concat(operacao));
		}
		
		return list;
	}

}
