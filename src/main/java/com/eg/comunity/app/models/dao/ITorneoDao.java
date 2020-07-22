package com.eg.comunity.app.models.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.eg.comunity.app.models.entity.Torneo;

public interface ITorneoDao extends CrudRepository<Torneo, Long> {

	
	public List<Torneo> findAllByOrderByFechaDesc();

	public Page<Torneo> findAll(Pageable pageable);

	public List<Torneo> findByNombreContainingIgnoreCaseOrderByNombreAsc(String nombre);
}
