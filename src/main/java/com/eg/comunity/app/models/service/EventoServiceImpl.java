package com.eg.comunity.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eg.comunity.app.models.dao.IEventoDao;
import com.eg.comunity.app.models.entity.Evento;

@Service
public class EventoServiceImpl implements IEventoService {
	
	@Autowired
	private IEventoDao eventoDao;

	@Override
	@Transactional(readOnly = true)
	public List<Evento> findAll() {
		return (List<Evento>) eventoDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Evento> findAll(Pageable pageable) {
		return eventoDao.findAll(pageable);
	}

	@Override
	@Transactional
	public void save(Evento evento) {
		eventoDao.save(evento);
	}

	@Override
	@Transactional(readOnly = true)
	public Evento findOne(Long id) {
		return eventoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		eventoDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Evento> findByNombre(String nombre) {
		return eventoDao.findByNombreContainingIgnoreCaseOrderByNombreAsc(nombre);
	}
	
}
