package com.eg.comunity.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eg.comunity.app.models.entity.Evento;


public interface IEventoService {
	
	public List<Evento> findAll();
	
	public Page<Evento> findAll(Pageable pageable);

	public void save(Evento evento);
	
	public Evento findOne(Long id);
	
	public void delete(Long id);

}
