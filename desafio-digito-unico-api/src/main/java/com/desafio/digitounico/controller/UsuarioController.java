package com.desafio.digitounico.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.desafio.digitounico.converters.UsuarioConverter;
import com.desafio.digitounico.dto.UsuarioDTO;
import com.desafio.digitounico.entities.Usuario;
import com.desafio.digitounico.services.AbstractService;
import com.desafio.digitounico.services.UsuarioService;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController extends AbstractController<Usuario, UsuarioDTO, Long> {

	@Autowired
	UsuarioService service;
	
	@Autowired
	UsuarioConverter converter;

	@Override
	protected AbstractService<Usuario, UsuarioDTO, Long> getService() {
		return this.service;
	}
}
