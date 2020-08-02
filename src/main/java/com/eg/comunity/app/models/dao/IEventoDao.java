package com.eg.comunity.app.models.dao;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.eg.comunity.app.models.entity.Evento;

public interface IEventoDao extends PagingAndSortingRepository<Evento, Long> {

	List<Evento> findByNombreContainingIgnoreCaseOrderByNombreAsc(String nombre);

}
