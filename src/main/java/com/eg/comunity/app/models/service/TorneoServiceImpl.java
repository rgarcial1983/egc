package com.eg.comunity.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eg.comunity.app.models.dao.ITorneoDao;
import com.eg.comunity.app.models.entity.Torneo;

@Service
public class TorneoServiceImpl implements ITorneoService{

	@Autowired
	private ITorneoDao torneoDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Torneo> findAllByOrderByFechaDesc() {
		return (List<Torneo>) torneoDao.findAllByOrderByFechaDesc();
	}

	@Override
	@Transactional
	public void save(Torneo torneo) {
		torneoDao.save(torneo);
	}

	@Override
	@Transactional(readOnly = true)
	public Torneo findOne(Long id) {
		return torneoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		torneoDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Torneo> findAll(Pageable pageable) {
		return torneoDao.findAll(pageable);
	}

	
	@Override
	@Transactional(readOnly = true)
	public List<Torneo> findByNombre(String nombre) {
		return torneoDao.findByNombreContainingIgnoreCaseOrderByNombreAsc(nombre);
	}

}
