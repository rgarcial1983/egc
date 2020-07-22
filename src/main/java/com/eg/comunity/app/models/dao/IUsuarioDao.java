package com.eg.comunity.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.eg.comunity.app.models.entity.Role;
import com.eg.comunity.app.models.entity.Usuario;

public interface IUsuarioDao extends CrudRepository<Usuario, Long>{

	public Usuario findByUsername(String username);
	
	public Usuario findByUsernameOrEmail(String username, String email);
	
	@Query("SELECT u.roles FROM Usuario u WHERE u.username = ?1")
	public List<Role> findRolesByUsername(String username);
	
}
