package com.desafio.digitounico.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.desafio.digitounico.utils.Nomenclatura;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = Nomenclatura.TABELA + "usuario")
@Getter @Setter @NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Usuario implements Persistable<Long> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario" + Nomenclatura.SEQUENCIA)
	@SequenceGenerator(name = "usuario" + Nomenclatura.SEQUENCIA, sequenceName = "usuario_id" + Nomenclatura.SEQUENCIA, allocationSize = 1)
	@Column(name = Nomenclatura.CHAVE_PRIMARIA +"usuario", nullable = false)
	private Long id;
	
	@NotBlank
	@Size(min = 3, max = 100)
	@Column(name = Nomenclatura.DESCRICAO +"nome", nullable = false)
	private String nome;
	
	@NotBlank
	@Size(min = 3, max = 100)
	@Column(name = Nomenclatura.DESCRICAO +"email", nullable = false)
	private String email;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "usuario", targetEntity = DigitoUnico.class)
	private Set<DigitoUnico> digitos = new HashSet<>();
	
}
