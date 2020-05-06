package com.desafio.digitounico.test;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.desafio.digitounico.controller.DigitoUnicoController;
import com.desafio.digitounico.dto.DigitoUnicoDTO;
import com.desafio.digitounico.dto.ParametrosDigitoDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(DigitoUnicoController.class)
@RunWith(SpringRunner.class)
public class DigitoUnicoControllerTest {

	private static final Long ID = 1L;
	private static final String DIGITOPARAM = "123";
	private static final Integer CONCATENACAO = 2;
	private static final Integer DIGITOGERADO = 3;
	private static final String URL = "/api/digitounico";
	private static final ObjectMapper OM = new ObjectMapper();

	@MockBean
	private DigitoUnicoController controller;

	@Autowired
	private MockMvc mvc;
	
	@Test
    public void getAll() throws Exception {
		DigitoUnicoDTO dto = DigitoUnicoDTO.builder().id(ID).digitoParam(DIGITOPARAM).concatenacao(CONCATENACAO).digitoGerado(DIGITOGERADO).idUsuario(ID).build();
        List<DigitoUnicoDTO> dtos = Arrays.asList(dto);

        given(controller.getAll()).willReturn(dtos);

        mvc.perform(get(URL + "/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
	
	@Test
	public void getAllById() throws Exception {
		DigitoUnicoDTO dto = DigitoUnicoDTO.builder().id(ID).digitoParam(DIGITOPARAM).concatenacao(CONCATENACAO).digitoGerado(DIGITOGERADO).idUsuario(ID).build();
		
		when(controller.getById(ID)).thenReturn(dto);
		
		mvc.perform(get(URL + "/" + ID)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	public void update() throws Exception {
		DigitoUnicoDTO dto = DigitoUnicoDTO.builder().id(ID).digitoParam(DIGITOPARAM).concatenacao(CONCATENACAO).digitoGerado(DIGITOGERADO).idUsuario(ID).build();
		DigitoUnicoDTO updateDto = DigitoUnicoDTO.builder().id(ID).digitoParam(DIGITOPARAM).concatenacao(CONCATENACAO).digitoGerado(DIGITOGERADO).idUsuario(3L).build();
		
		when(controller.update(ID, dto)).thenReturn(new ResponseEntity<>(updateDto, HttpStatus.CREATED));

        mvc.perform(put(URL + "/" + ID)
                .content(OM.writeValueAsBytes(updateDto))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
	}
	
	@Test
    public void deleteById() throws Exception {
        when(controller.deleteById(ID))
        .thenReturn(new ResponseEntity<>(ID, HttpStatus.OK));

        mvc.perform(delete(URL + "/" + ID)
        		.contentType(MediaType.APPLICATION_JSON)
        		.accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
	
	@Test
	public void getAllByUsuario() throws Exception {
		DigitoUnicoDTO dto = DigitoUnicoDTO.builder().id(ID).digitoParam(DIGITOPARAM).concatenacao(CONCATENACAO).digitoGerado(DIGITOGERADO).idUsuario(ID).build();
		List<DigitoUnicoDTO> digitos = Arrays.asList(dto);

		given(controller.getAllByUsuario(ID)).willReturn(digitos);
		
		mvc.perform(get(URL + "/{id}")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	public void createDigito() throws Exception {
		ParametrosDigitoDTO dto = ParametrosDigitoDTO.builder().digitoParam(DIGITOPARAM).concatenacao(CONCATENACAO).idUsuario(ID).build();
		DigitoUnicoDTO dtoSalvo = DigitoUnicoDTO.builder().id(ID).digitoParam(DIGITOPARAM).concatenacao(CONCATENACAO).digitoGerado(DIGITOGERADO).idUsuario(ID).build();
		
		when(controller.createDigito(dto))
        .thenReturn(new ResponseEntity<>(dtoSalvo, HttpStatus.CREATED));

		mvc.perform(post(URL + "/calcular")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"digitoParam\": " + DIGITOPARAM 
                		+ ", \"concatenacao\": " + CONCATENACAO + " }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
	}
	
	@Test
	public void createDigitoByUsuario() throws Exception {
		ParametrosDigitoDTO dto = ParametrosDigitoDTO.builder().digitoParam(DIGITOPARAM).concatenacao(CONCATENACAO).idUsuario(ID).build();
		DigitoUnicoDTO dtoSalvo = DigitoUnicoDTO.builder().id(ID).digitoParam(DIGITOPARAM).concatenacao(CONCATENACAO).digitoGerado(DIGITOGERADO).idUsuario(ID).build();
		
		when(controller.createDigito(dto))
        .thenReturn(new ResponseEntity<>(dtoSalvo, HttpStatus.CREATED));

		mvc.perform(post(URL + "/calcular/usuario")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"digitoParam\": " + DIGITOPARAM 
                		+ ", \"concatenacao\": " + CONCATENACAO 
                		+ ", \\\"idUsuario\\\": " + ID + " }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
	}

}
