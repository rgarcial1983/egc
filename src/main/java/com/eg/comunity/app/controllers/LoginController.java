package com.eg.comunity.app.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	public String nuevoUsuario(@PathVariable(value = "accion") String accion, Usuario usuario,Model model, Principal principal, RedirectAttributes flash) {
		String retorno = !"".equals(accion) ? "usuario/listar" : "redirect:/login";
		
		// Revisamos si el usuario ya existe
		Usuario userAux = usuarioDao.findByUsernameOrEmail(usuario.getUsername(), usuario.getEmail());
		if(userAux != null) {
			flash.addFlashAttribute("info", "Ya existe un usuario con ese nick/alias");
			model.addAttribute("error", "Error en el login: Nombre de usuario o contraseña incorrecta, por favor vuelva a intentarlo!");
			return retorno;
		}
		
		String mensajeFlash = (usuario.getId() != null) ? "Usuario editado con éxito!!" : "Usuario creado con éxito!";

		// Codificamos la clave
		usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
		System.out.println(usuario.getPassword());
		
		// Activamos el usuario
		usuario.setEnabled(true);
		
		userAux = usuarioDao.findByUsername("user");
        usuario.setRoles(usuarioDao.findRolesByUsername("user"));
		
		usuarioDao.save(usuario);
		flash.addFlashAttribute("success", mensajeFlash);
		
		return retorno;
	}
	
	
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
				return "redirect:/usuario";
			}
		} else {
			flash.addFlashAttribute("error", "El ID del usuario no puede ser cero!");
			return "redirect:/listar";
		}
		model.put("usuario", usuario);
		model.put("titulo", "Editar Usuario");
		return "usuario";
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

			/*if (uploadFileService.delete(cliente.getFoto())) {
				flash.addFlashAttribute("info", "Foto " + cliente.getFoto() + " eliminada con exito!");
			}*/

		}
		return "redirect:/usuario/listar";
	}
}
