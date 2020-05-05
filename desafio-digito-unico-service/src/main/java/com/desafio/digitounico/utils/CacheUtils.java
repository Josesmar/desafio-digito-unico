package com.desafio.digitounico.utils;

import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;

public class CacheUtils {
	
	private static LinkedHashMap<String, Integer> CACHE = new LinkedHashMap<>();
	
	public static void adicionar(String digito, Integer concatenacao, Integer digitoUnico) {
		if (CACHE.size() == 10) {
			removerChaveAntiga();
		}
		
		String chave = gerarNovaChave(digito, concatenacao);
		if (!procurarChave(chave)) {
			CACHE.put(chave, digitoUnico);
		}
	}
	
	public static Integer buscar(String digito, Integer concatenacao) {
		Integer digitoUnico = null;
		String chave = gerarNovaChave(digito, concatenacao);
		if (procurarChave(chave)) {
			digitoUnico = CACHE.get(chave);
		}
		
		return digitoUnico;
	}
	
	private static String gerarNovaChave(String digito, Integer concatenacao) {
		return digito.concat("-").concat(String.valueOf(concatenacao));
	}
	
	private static void removerChaveAntiga() {
		String chaveAntiga = CACHE.keySet().stream().findFirst().get();
		CACHE.remove(chaveAntiga);
	}
	
	private static boolean procurarChave(String chave) {
		return CACHE.containsKey(chave);
	}
	
	public static Set<Entry<String, Integer>> getCache() {
		return CACHE.entrySet();
	}
}
