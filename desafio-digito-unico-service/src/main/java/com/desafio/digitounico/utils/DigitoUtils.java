package com.desafio.digitounico.utils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class DigitoUtils {

	public static Integer calcular(String digito, Integer concatenacao) {
		if (StringUtils.isBlank(digito) && concatenacao == null) {
			return null;
		} else if (concatenacao == null || concatenacao <= 0) {
			return somarDigitos(new BigInteger(digito));
		} 
		
		String digitoConcatenado = String.valueOf("");
		
		for (int i = 0; i < concatenacao; i++) {
			digitoConcatenado = digitoConcatenado.concat(digito);
		}
		
		return somarDigitos(new BigInteger(digitoConcatenado));
	}

	private static Integer somarDigitos(BigInteger digitoConcatenado) {
		List<BigInteger> digitos = integerToList(digitoConcatenado);
		BigInteger result = digitos.stream().reduce(BigInteger.ZERO, (subtotal, digito) -> subtotal.add(digito));
		if (new BigInteger("9").compareTo(result) < 0) {
			return somarDigitos(result);
		}
		
		return result.intValue();
	}

	private static List<BigInteger> integerToList(BigInteger digitoConcatenado) {
		List<BigInteger> digitos = new ArrayList<>();
		char[] strDigitos = digitoConcatenado.toString().toCharArray();
		for (char strDigito : strDigitos) {
			String algarismo = String.valueOf(strDigito);
			digitos.add(new BigInteger(algarismo));
		}
		
		return digitos;
	}
	
}
