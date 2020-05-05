package com.desafio.digitounico.utils;

import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;

public class CacheUtils {

	private static LinkedHashMap<String, Integer> cache = new LinkedHashMap<>();

	private CacheUtils() {
		throw new IllegalStateException("Utility class");
	}

	public static void adicionar(String digito, Integer concatenacao, Integer digitoUnico) {
		if (cache.size() == 10) {
			removerChaveAntiga();
		}

		String chave = gerarNovaChave(digito, concatenacao);
		if (!procurarChave(chave)) {
			cache.put(chave, digitoUnico);
		}
	}

	public static Integer buscar(String digito, Integer concatenacao) {
		Integer digitoUnico = null;
		String chave = gerarNovaChave(digito, concatenacao);
		if (procurarChave(chave)) {
			digitoUnico = cache.get(chave);
		}

		return digitoUnico;
	}

	private static String gerarNovaChave(String digito, Integer concatenacao) {
		return digito.concat("-").concat(String.valueOf(concatenacao));
	}

	private static void removerChaveAntiga() {
		String chaveAntiga = cache.keySet().stream().findFirst().orElse(null);
		cache.remove(chaveAntiga);
	}

	private static boolean procurarChave(String chave) {
		return cache.containsKey(chave);
	}

	public static Set<Entry<String, Integer>> getCache() {
		return cache.entrySet();
	}
}
