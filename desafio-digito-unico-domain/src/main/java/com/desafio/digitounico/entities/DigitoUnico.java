package com.desafio.digitounico.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import com.desafio.digitounico.utils.Nomenclatura;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = Nomenclatura.TABELA + "digito_unico")
@Getter @Setter @NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class DigitoUnico implements Persistable<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "digito_unico" + Nomenclatura.SEQUENCIA)
	@SequenceGenerator(name = "digito_unico" + Nomenclatura.SEQUENCIA, sequenceName = "digito_unico_id" + Nomenclatura.SEQUENCIA, allocationSize = 1)
	@Column(name = Nomenclatura.CHAVE_PRIMARIA +"digito_unico", nullable = false)
	private Long id;
	
	@Size(min = 3, max = 100)
	@Column(name = Nomenclatura.DESCRICAO +"digito", nullable = false)
	private String digitoParam;
	
	@Min(1)
	@Column(name = Nomenclatura.NUMERICO + "concatenacao", nullable = true)
	private Integer concatenacao;
	
	@Min(1)
	@Column(name = Nomenclatura.NUMERICO + "digitoGerado", nullable = false)
	private Integer digitoGerado;
	
	@JoinColumn(name = Nomenclatura.CHAVE_PRIMARIA + "usuario", nullable = true, 
				foreignKey = @ForeignKey(name = Nomenclatura.CHAVE_SECUNDARIA + "digito_unico_usuario"))
	@ManyToOne(fetch = FetchType.LAZY)
	private Usuario usuario;

}
