package com.desafio.digitounico.utils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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

		if (!StringUtils.isNumeric(dto.getDigitoParam())) {
			list.add("O dígito informado contém caracteres não numéricos");
		}

		BigInteger concatMax = BigInteger.TEN.pow(5);
		if (Objects.nonNull(dto.getConcatenacao()) && (dto.getConcatenacao() < 1
				|| (concatMax.compareTo(BigInteger.valueOf(dto.getConcatenacao())) < 0))) {
			list.add("Concatenação excede o máximo(10^5).");
		}

		BigInteger tamanhoMax = BigInteger.TEN.pow(1000000);
		if ((new BigInteger(dto.getDigitoParam()).compareTo(BigInteger.ONE) < 0)
				|| (tamanhoMax.compareTo(new BigInteger(dto.getDigitoParam())) < 0)) {
			list.add("O dígito informado excede o máximo(10^^1000000).");
		}

		return list;
	}

	public static ResponseEntity<String> validarCriptografiaUsuario(Long id, boolean isCriptografia) {
		List<String> erros = listarErrosCriptografia(id, isCriptografia);
		if (CollectionUtils.isNotEmpty(erros)) {
			String body = "";
			for (String string : erros) {
				body = body.concat(" ").concat(string);
			}
			return ResponseEntity.badRequest().body(body);
		}

		return ResponseEntity.ok().body("OK");
	}

	private static List<String> listarErrosCriptografia(Long id, boolean isCriptografia) {
		List<String> list = new ArrayList<>();
		if (Objects.isNull(id)) {
			list.add("O id usuário não pode ser nulo!");
		}

		if ((isCriptografia && CriptografiaUtils.usuarioPossuiChave(id))) {
			list.add("O usuário já foi criptografado!");
		}
		
		if ((!isCriptografia && !CriptografiaUtils.usuarioPossuiChave(id))) {
			list.add("O usuário ainda não foi criptografado!");
		}

		return list;
	}

}
