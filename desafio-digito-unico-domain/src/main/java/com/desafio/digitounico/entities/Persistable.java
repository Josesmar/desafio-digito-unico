package com.desafio.digitounico.entities;

import java.io.Serializable;

public interface Persistable<PK extends Serializable> {
	/**
	 * Retorna a chave primaria da entidade
	 * @return id chave primaria
	 */
	public abstract PK getId();

	/**
	 * Seta a chave primaria da entidade que pode ser simples ou composta
	 * @param id chave primaria
	 */
	public abstract void setId(PK id);

}