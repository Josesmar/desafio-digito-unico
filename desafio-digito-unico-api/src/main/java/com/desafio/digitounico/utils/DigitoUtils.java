package com.desafio.digitounico.utils;

import org.apache.commons.lang3.StringUtils;

public class DigitoUtils {

	public static Integer somarDigitos(String digitos) {
		Integer digitoUnico = 0;
		for (int i = 0; i < digitos.length(); i++) {
			digitoUnico += Integer.valueOf(digitos.charAt(i));
		}
		if (StringUtils.length(String.valueOf(digitoUnico)) == 1) {
			return digitoUnico;
		} 
		return somarDigitos(String.valueOf(digitoUnico));
	}
}
