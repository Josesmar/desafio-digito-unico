package com.desafio.digitounico.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.desafio.digitounico.entities.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	@Override
	@Query("SELECT DISTINCT u FROM Usuario u LEFT JOIN FETCH u.digitos")
	public List<Usuario> findAll();
	
	@Override
	@Query("SELECT DISTINCT u FROM Usuario u LEFT JOIN FETCH u.digitos WHERE u.id = (:id)")
	public Optional<Usuario> findById(@Param("id") Long id);

	@Query("SELECT DISTINCT COUNT(u.id) FROM Usuario u WHERE u.id = (:id)")
	public Integer validarUsuario(@Param("id") Long id);

}
