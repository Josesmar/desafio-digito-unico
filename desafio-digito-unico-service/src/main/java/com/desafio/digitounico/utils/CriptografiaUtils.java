package com.desafio.digitounico.utils;

import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.management.openmbean.InvalidKeyException;

import com.desafio.digitounico.entities.Usuario;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CriptografiaUtils {
	private static final String UTF8 = "UTF8";
	private static final String ALGORITMO_RSA = "RSA";
	private static final String GENERAL_ERROR = "Texto muito longo ou chave inválida";
	private static final String GERAR_CHAVES_ERROR = "Falha ao gerar chaves para criptografia";
	private static final Map<Long, KeyPair> keys = new HashMap<>();
	private static final Decoder DECODER = Base64.getDecoder();
	private static final Encoder ENCODER = Base64.getEncoder();

	public static void gerarChaves(Long idUsuario) throws GeneralSecurityException {
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITMO_RSA);
			keyGen.initialize(2048);
			KeyPair keyPair = keyGen.generateKeyPair();
			keys.put(idUsuario, keyPair);
		} catch (Exception e) {
			log.error("<< CriptografiaService.criptografar [error={}]", GERAR_CHAVES_ERROR);
			throw new GeneralSecurityException(GERAR_CHAVES_ERROR);
		}
	}

	public static Usuario criptografar(Usuario usuario) {
		log.debug(" >> CriptografiaService.criptografar [entity={}] ", usuario.getClass().getName());
		PublicKey chavePublica = keys.get(usuario.getId()).getPublic();
		usuario.setNome(criptografarTexto(usuario.getNome(), chavePublica));
		usuario.setEmail(criptografarTexto(usuario.getEmail(), chavePublica));
		log.debug(" << CriptografiaService.criptografar [entity={}] ", usuario.getClass().getName());
		return usuario;
	}

	private static String criptografarTexto(String texto, PublicKey chavePublica) {
		try {
			Cipher cipher = Cipher.getInstance(ALGORITMO_RSA);
			cipher.init(Cipher.ENCRYPT_MODE, chavePublica);
			byte[] textoBytes = cipher.doFinal(texto.getBytes());
			return ENCODER.encodeToString(textoBytes);
		} catch (Exception e) {
			log.error("<< CriptografiaService.criptografar [error={}]", GENERAL_ERROR);
			throw new InvalidKeyException(GENERAL_ERROR);
		}
	}

	public static Usuario descriptografar(Usuario usuario) {
		log.debug(" >> CriptografiaService.descriptografar [entity={}] ", usuario.getClass().getName());
		PrivateKey chavePrivada = keys.get(usuario.getId()).getPrivate();
		usuario.setNome(descriptografarTexto(usuario.getNome(), chavePrivada));
		usuario.setEmail(descriptografarTexto(usuario.getEmail(), chavePrivada));
		return usuario;
	}

	private static String descriptografarTexto(String textoCriptografado, PrivateKey chavePrivada) {
		try {
			Cipher cipher = Cipher.getInstance(ALGORITMO_RSA);
			cipher.init(Cipher.DECRYPT_MODE, chavePrivada);
			byte[] byteDecode = DECODER.decode(textoCriptografado);
			return new String(cipher.doFinal(byteDecode), UTF8);
		} catch (Exception e) {
			log.error("<< CriptografiaService.descriptografar [error={}]", GENERAL_ERROR);
			throw new InvalidKeyException(GENERAL_ERROR);
		}
	}
	
	public static boolean checarUsuario(Long idUsuario) {
		return keys.containsKey(idUsuario);
	}
}
