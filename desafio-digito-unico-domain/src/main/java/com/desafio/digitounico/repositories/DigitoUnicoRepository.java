package com.desafio.digitounico.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.desafio.digitounico.entities.DigitoUnico;

@Repository
public interface DigitoUnicoRepository extends JpaRepository<DigitoUnico, Long> {
	
	@Query("SELECT DISTINCT du FROM DigitoUnico du WHERE du.usuario.id = (:idUsuario)")
	public List<DigitoUnico> getAllByUsuario(@Param("idUsuario") Long idUsuario); 
	
	@Query("SELECT DISTINCT COUNT(du.id) FROM DigitoUnico du WHERE du.digitoGerado = (:digitoGerado)")
	public int validarDigito(@Param("digitoGerado") Integer digitoGerado);
}
