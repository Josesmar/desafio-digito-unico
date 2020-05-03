package com.desafio.digitounico.utils;

import com.desafio.digitounico.dto.CriptografiaDTO;

public class CriptografiaUtils {
	
	// TO DO 
	public static CriptografiaDTO gerarChaves() {
		CriptografiaDTO dto = new CriptografiaDTO();
		return dto;
	}

	// TO DO
	public static byte[] criptografar(String textoPuro, String chavePublica) {
		byte[] result = null;
		return result;
	}
	
	// TO DO
	public static String descriptografar(byte[] textoCriptografado, String chavePrivada) {
		return "";
	}
	
	public boolean validarChaves(String chavePublica, String chavePrivada) {
		return Boolean.TRUE;
	}
	
}
