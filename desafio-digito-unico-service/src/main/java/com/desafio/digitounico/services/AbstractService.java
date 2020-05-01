package com.desafio.digitounico.services;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.desafio.digitounico.converters.Converter;
import com.desafio.digitounico.dto.BaseDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractService<T extends Persistable<PK>, DTO extends BaseDTO, PK extends Serializable> {
	
	/**
	 * Somente deve ser usado para habilitar os filtros do servico
	 */
	@PersistenceContext
	public EntityManager entityManager;
	
	/**
	 * Repositorio generico
	 * @return
	 */
	protected abstract JpaRepository<T, PK> getRepository();
	
	/**
	 * Conversor
	 * @return
	 */
	protected abstract Converter<T, DTO> getConverter();
	
	
	/**
	 * Realiza uma contagem em todos os registros da base
	 * @return a quantidade de registros
	 */
	public boolean isEmpty() {
		log.debug(">> isEmpty");
		boolean isEmpty = getRepository().count() == 0;
		log.debug("<< isEmpty [isEmpty={}] ", isEmpty);
		return isEmpty;
	}
	
	/**
	 * Realiza uma contagem em todos os registros da base
	 * @return a quantidade de registros
	 */
	public Long count() {
		log.debug(">> count");
		long count = getRepository().count();
		log.debug("<< count [count={}] ", count);
		return count;
	}

	/**
	 * Faz a pesquisa utilizando o id que representa a classe
	 * @param id que representa a chave primaria
	 * @return O objeto especifico
	 */
	public DTO findById(PK id) {
		log.debug(">> findById [id={}] ", id);
		Optional<T> entity = getRepository().findById(id);
		log.debug("<< findById [id={}, entity={}] ", id, entity);
		Optional<DTO> dto = entity.isPresent() ? Optional.of(getConverter().convertToDTO(entity.get())) : Optional.empty();
		log.debug("<< findById [id={}, entity={}, dto={}] ", id, entity, dto);
		return dto.orElseThrow(EntityNotFoundException::new);
	}
	
	/**
	 * Busca por todos os registros de uma determinada classe
	 * @return A lista de todos os objetos 
	 */
	public List<DTO> findAll(){
		log.debug(">> findAll");
		List<T> entities = getRepository().findAll();
		log.debug("<< findAll [entities={}] ", entities);
		List<DTO> dtos = entities.parallelStream().map(entity -> getConverter().convertToDTO(entity)).collect(Collectors.toList());
		log.debug("<< findAll [dtos={}] ", dtos);
		return dtos;
	}
	
	/**
	 * Salva uma nova entidade no banco
	 * @param entity a entidade generica a ser criada
	 * @return a entity salva no banco
	 */
	public DTO save(DTO dto) {
		log.debug(">> save [dto={}] ", dto);
		T entity = getConverter().convertToEntity(dto);
		log.debug(">> save [entity={}, dto={}] ", entity, dto);
		T t = getRepository().save(entity);
		log.debug("<< save [entity={}] ", t);
		dto =  getConverter().convertToDTO(t);
		log.debug("<< save [dto={}] ", dto);
		return dto;
	}
	
	/**
	 * Atualiza uma determinada entidade
	 * @param id o id da entidade a ser atualizada
	 * @param entity a entidade com os dados a ser atualizado
	 * @return a entidade atualizada
	 */
	public DTO update(PK id, DTO entity) {
		log.debug(">> update [entity={}] ", entity);
		DTO dto = this.findById(id);
		log.debug("<< update [dto={}] ", dto);
		dto		= getConverter().convertToClone(entity, dto);
		log.debug("<< update [dto={}] ", dto);
		T t 	= getRepository().save(getConverter().convertToEntity(dto));
		log.debug("<< update [entity={}] ", t);
		entity = getConverter().convertToDTO(t);
		log.debug("<< update [entity={}] ", entity);
		return entity;
	}
	
	
	/**
	 * Exclua uma entidade cujo id exista no banco
	 * @param id
	 */
	public void delete(PK id) {
		log.debug(">> delete [id={}] ", id);
		getRepository().deleteById(id);
	}
	
}
