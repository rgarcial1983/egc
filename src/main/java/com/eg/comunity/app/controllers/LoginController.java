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

import com.eg.comunity.app.email.EnvioMail;
import com.eg.comunity.app.models.dao.IUsuarioDao;
import com.eg.comunity.app.models.entity.Usuario;

@Controller
public class LoginController {
	
	@Autowired
	private IUsuarioDao usuarioDao;
	
	@Autowired
	private EnvioMail mailService;
	
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
	public String nuevoUsuario(@PathVariable(value = "accion") String accion, @Valid Usuario usuario, BindingResult result, Model model, Principal principal, RedirectAttributes flash) {
		
		// Validamos los campos obligatorios
		if(result.hasErrors()) {
			return "usuario/form";
		}
		
		String retorno = !"".equals(accion) ? "redirect:/usuario/listar" : "redirect:/login";
		
		// Revisamos si el usuario ya existe
		Usuario userAux = usuarioDao.findByUsernameOrEmail(usuario.getUsername(), usuario.getEmail());
		if(userAux != null && userAux.getEnabled() && (usuario.getId() != userAux.getId())) {
			flash.addFlashAttribute("info", "El nick/alias o el email ya existen");
			flash.addFlashAttribute("mensajeInfo", "El nick/alias o el email ya existen");
			model.addAttribute("error", "El nick/alias o el email ya existen");
			return retorno;
		}
		
		String mensajeFlash = (usuario.getId() != null) ? "Usuario editado con éxito!!" : "Usuario creado con éxito!";
		
		// Activamos el usuario
		usuario.setEnabled(true);
		
		// Si es un usuario nuevo se codifica la clave
		if(usuario.getId() == null  || "cambiarClave".equals(accion)) {
			// Codificamos la clave
			String clave = usuario.getPassword();
			usuario.setPassword(passwordEncoder.encode(clave));

			// Agregamos su rol
			usuario.setRoles(usuarioDao.findRolesByUsername("user"));
			
			// Envio email
			mailService.enviarEmailNuevoUsuario(usuario, clave);
		} else {
			usuario.setPassword(userAux.getPassword());
			usuario.setRoles(userAux.getRoles());
		}
		
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
	public String crearNuevoUsuario(@Valid Usuario usuario, BindingResult result, Model model, RedirectAttributes flash) {
		String mensajeFlash = (usuario.getId() != null) ? "Usuario editado con éxito!!" : "Usuario creado con éxito!";
		String clave = usuario.getPassword();
		
		// Validamos los campos obligatorios
		if(result.hasErrors()) {
			return "redirect:/login";
		}
		
		// Codificamos la clave
		usuario.setPassword(passwordEncoder.encode(clave));
		System.out.println(usuario.getPassword());
		
		// Activamos el usuario
		usuario.setEnabled(true);
		
		// Metemos los roles
        usuario.setRoles(usuarioDao.findRolesByUsername("user"));
		
		usuarioDao.save(usuario);
		
		// Envio email
		mailService.enviarEmailNuevoUsuario(usuario, clave);
					
		flash.addFlashAttribute("success", mensajeFlash);
		
		return "redirect:/login";
	}
	
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/cambiarClave/{id}")
	public String irCambiarClaveUsurio(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		Usuario usuario = null;

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
		// Reseteamos la clave
		usuario.setPassword("");
		
		model.put("usuario", usuario);
		model.put("titulo", "Cambiar Password");
		
		return "usuario/resetPassword";
	}
}
