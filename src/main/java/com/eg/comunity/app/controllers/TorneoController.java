package com.eg.comunity.app.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.eg.comunity.app.models.entity.Torneo;
import com.eg.comunity.app.models.service.ITorneoService;
import com.eg.comunity.app.models.service.IUploadFileService;

@Controller
public class TorneoController {
	
	@Autowired
	private ITorneoService torneoService;
	
	@Autowired
	private IUploadFileService uploadFileService;


	@RequestMapping(value = {"torneo/listar"}, method = RequestMethod.GET)
	public String listarTorneos(Model model) {
		
		List<Torneo> torneos = torneoService.findAllByOrderByFechaDesc();
		model.addAttribute("torneos", torneos);
		
		return "torneo/listar";
	}
	
	@RequestMapping(value = {"/torneo"}, method = RequestMethod.GET)
	public String formularioTorneo(Model model) {
		
		model.addAttribute("torneo", new Torneo());
		model.addAttribute("titulo", "Crear torneo");
		
		return "torneo/form";
	}
	
	@RequestMapping("/buscarTorneo")
	public String buscarTorneo(@RequestParam("q") String consulta, Model model) {
		List<Torneo> torneos = torneoService.findByNombre(consulta);
		model.addAttribute("torneos", torneos);
		return "torneo/listar";
	}
	
	@RequestMapping(value = "/torneo/{id}")
	public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		Torneo torneo = null;

		if (id > 0) {
			torneo = torneoService.findOne(id);
			if (torneo == null) {
				flash.addFlashAttribute("error", "El ID del torneo no existe en la BBDD!");
				return "redirect:/listar";
			}
		} else {
			flash.addFlashAttribute("error", "El ID del torneo no puede ser cero!");
			return "redirect:torneo/listar";
		}
		model.put("torneo", torneo);
		model.put("titulo", "Editar Torneo");
		return "torneo/form";
	}
	
	@RequestMapping(value = "/torneo/form", method = RequestMethod.POST)
	public String guardar(@Valid Torneo torneo, BindingResult result, Model model,
			@RequestParam("file") MultipartFile foto, RedirectAttributes flash, SessionStatus status) {

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Nuevo Torneo");
			return "torneo/form";
		}

		if (!foto.isEmpty()) {

			if (torneo.getId() != null && torneo.getId() > 0 && torneo.getFoto() != null && torneo.getFoto().length() > 0) {
				uploadFileService.delete(torneo.getFoto());
			}

			String uniqueFilename = null;
			try {
				uniqueFilename = uploadFileService.copy(foto);
			} catch (IOException e) {
				e.printStackTrace();
			}

			flash.addFlashAttribute("info", "Has subido correctamente '" + uniqueFilename + "'");
			torneo.setFoto(uniqueFilename);
		}
		
		String mensajeFlash = (torneo.getId() != null) ? "Torneo editado con éxito!" : "Torneo creado con éxito!";

		torneoService.save(torneo);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		model.addAttribute("mensajeFlash", mensajeFlash);
		return "redirect:/torneo/listar";
	}
	
	@RequestMapping(value = "/torneo/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

		if (id > 0) {
			Torneo torneo = torneoService.findOne(id);

			torneoService.delete(id);
			flash.addFlashAttribute("success", "Cliente eliminado con éxito!");

			if (uploadFileService.delete(torneo.getFoto())) {
				flash.addFlashAttribute("info", "Foto " + torneo.getFoto() + " eliminada con exito!");
			}

		}
		return "redirect:/torneo/listar";
	}
	
}
