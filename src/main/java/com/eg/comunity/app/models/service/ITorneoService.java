package com.eg.comunity.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eg.comunity.app.models.entity.Torneo;

public interface ITorneoService {

	public List<Torneo> findAllByOrderByFechaDesc();
	
	public Page<Torneo> findAll(Pageable pageable);

	public void save(Torneo cliente);
	
	public Torneo findOne(Long id);
	
	public void delete(Long id);
	
	public List<Torneo> findByNombre(String nombre);

}
