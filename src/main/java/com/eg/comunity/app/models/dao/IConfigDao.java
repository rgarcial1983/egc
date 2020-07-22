package com.eg.comunity.app.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.eg.comunity.app.models.entity.Configuracion;

public interface IConfigDao extends CrudRepository<Configuracion, Long>{
	
}
