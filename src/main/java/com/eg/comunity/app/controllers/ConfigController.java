package com.eg.comunity.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.eg.comunity.app.models.dao.IConfigDao;
import com.eg.comunity.app.models.entity.Configuracion;

@Controller
public class ConfigController {
	
	@Autowired
	private IConfigDao configDao;

	@GetMapping(value = "/configuracion")
	public String mostrarConfiguracion(Model model) {
		
		Configuracion config = configDao.findById(1L).orElse(null);
		
		model.addAttribute("config", config);
		model.addAttribute("titulo", "Configuración de contacto");
		
		return "configuracion/config";
	}
	
	@PostMapping(value = "/configuracion/edit")
	public String editarConfiguracion(Configuracion config, Model model, RedirectAttributes flash) {
		
		configDao.save(config);
		flash.addFlashAttribute("success", "Se ha actualizado corrrectamente");
		
		return "redirect:/torneo/listar";
	}
}
