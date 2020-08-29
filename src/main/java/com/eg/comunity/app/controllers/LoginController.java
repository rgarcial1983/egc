package com.eg.comunity.app.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.eg.comunity.app.models.dao.IUsuarioDao;
import com.eg.comunity.app.models.entity.Usuario;

@Controller
public class LoginController {
	
	@Autowired
	private IUsuarioDao usuarioDao;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@GetMapping("/login")
	public String login(@RequestParam(value="error", required=false) String error,
			@RequestParam(value="logout", required = false) String logout,
			Model model, Principal principal, RedirectAttributes flash) {
		
		if(principal != null) {
			flash.addFlashAttribute("info", "Ya ha inciado sesión anteriormente");
			return "redirect:/";
		}
		
		if(error != null) {
			model.addAttribute("error", "Error en el login: Nombre de usuario o contraseña incorrecta, por favor vuelva a intentarlo!");
		}
		
		if(logout != null) {
			model.addAttribute("success", "Ha cerrado sesión con éxito!");
		}
		
		// Inicializamos el usuario
		Usuario usuario = new Usuario();
		model.addAttribute("usuario", usuario);
		
		return "login";
	}
	
	
	@PostMapping(value = {"/usuario", "/usuario/accion={accion}"})
	public String nuevoUsuario(@PathVariable(value = "accion") String accion, @Valid Usuario usuario, Model model, Principal principal, RedirectAttributes flash, BindingResult result) {
		
		// Validamos los campos obligatorios
		if(result.hasErrors()) {
			return "login";
		}
		
		String retorno = !"".equals(accion) ? "redirect:/usuario/listar" : "redirect:/login";
		
		// Traemos el usuario de BBDD que tenga ese id
		//Usuario userBD = usuarioDao.findById(usuario.getId()).orElse(null);
		
		// Revisamos si el usuario ya existe
		Usuario userAux = usuarioDao.findByUsernameOrEmail(usuario.getUsername(), usuario.getEmail());
		if(userAux != null && (usuario.getId() != userAux.getId())) {
			flash.addFlashAttribute("info", "El nick/alias o el email ya existen");
			flash.addFlashAttribute("mensajeInfo", "El nick/alias o el email ya existen");
			model.addAttribute("error", "El nick/alias o el email ya existen");
			return retorno;
		}
		
		String mensajeFlash = (usuario.getId() != null) ? "Usuario editado con éxito!!" : "Usuario creado con éxito!";

		// Codificamos la clave
		usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
		System.out.println(usuario.getPassword());
		
		// Activamos el usuario
		usuario.setEnabled(true);
		
        usuario.setRoles(usuarioDao.findRolesByUsername("user"));
		
		usuarioDao.save(usuario);
		flash.addFlashAttribute("success", mensajeFlash);
		
		return retorno;
	}
	
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = {"usuario/listar"}, method = RequestMethod.GET)
	public String listarUsuarios(Model model) {
		
		List<Usuario> usuarios = new ArrayList<Usuario>();
		List<Usuario> usuariosTotales = (List<Usuario>)usuarioDao.findAll();
		for(Usuario usuario : usuariosTotales) {
			if (usuario.getEnabled()) {
				usuarios.add(usuario);
			}
		}
		
		model.addAttribute("titulo", "Listado de Usuarios");
		model.addAttribute("usuarios", usuarios);
		model.addAttribute("usuario", new Usuario());
		
		return "usuario/listar";
	}
	
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/usuario/{id}")
	public String editarUsurio(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		Usuario usuario = null;
		String mensajeFlash = "";

		if (id > 0) {
			usuario = usuarioDao.findById(id).orElse(null);
			if (usuario == null) {
				flash.addFlashAttribute("error", "El ID del usuario no existe en la BBDD!");
				flash.addFlashAttribute("mensajeDanger", "El ID del usuario no existe en la BBDD!");
				return "redirect:/usuario";
			}
		} else {
			flash.addFlashAttribute("error", "El ID del usuario no puede ser cero!");
			flash.addFlashAttribute("mensajeDanger", "El ID del usuario no puede ser cero!");
			return "redirect:/listar";
		}
		model.put("usuario", usuario);
		model.put("titulo", "Editar Usuario");
		
		mensajeFlash = (usuario.getId() != null) ? "Usuario editado con éxito!" : "Usuario creado con éxito!";
		flash.addFlashAttribute("success", mensajeFlash);
		
		return "usuario/form";
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping(value = "/usuario/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

		if (id > 0) {
			Usuario usuario = usuarioDao.findById(id).orElse(null);
			// Lo borramos de manera lógia
			usuario.setEnabled(false);
			usuarioDao.save(usuario);
			flash.addFlashAttribute("success", "Usuario eliminado con éxito!");

		}
		return "redirect:/usuario/listar";
	}

	@RequestMapping(value = {"/usuarioNuevo"}, method = RequestMethod.GET)
	public String formularioUsuario(Model model) {
		
		model.addAttribute("usuario", new Usuario());
		model.addAttribute("titulo", "Nuevo Usuario");
		
		return "usuario/form";
	}
	
	@PostMapping(value = {"/usuario/create"})
	public String crearNuevoUsuario(Usuario usuario, Model model, RedirectAttributes flash) {
		String mensajeFlash = (usuario.getId() != null) ? "Usuario editado con éxito!!" : "Usuario creado con éxito!";

		// Codificamos la clave
		usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
		System.out.println(usuario.getPassword());
		
		// Activamos el usuario
		usuario.setEnabled(true);
		
		// Metemos los roles
        usuario.setRoles(usuarioDao.findRolesByUsername("user"));
		
		usuarioDao.save(usuario);
		flash.addFlashAttribute("success", mensajeFlash);
		
		return "redirect:/login";
	}
}
