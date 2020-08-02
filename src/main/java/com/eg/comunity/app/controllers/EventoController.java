package com.eg.comunity.app.controllers;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.eg.comunity.app.models.entity.Evento;
import com.eg.comunity.app.models.service.IEventoService;
import com.eg.comunity.app.util.paginator.PageRender;

@Controller
public class EventoController {
	
	protected final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private IEventoService eventoService;
	
	@RequestMapping(value = {"/", "/evento/listar"}, method = RequestMethod.GET)
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model,
			Authentication authentication,
			HttpServletRequest request) {	
		
		Pageable pageRequest = PageRequest.of(page, 5);

		Page<Evento> eventos = eventoService.findAll(pageRequest);

		PageRender<Evento> pageRender = new PageRender<Evento>("/eventos", eventos);
		model.addAttribute("titulo", "Listado de Eventos");
		model.addAttribute("eventos", eventos);
		model.addAttribute("page", pageRender);
		return "evento/listar";
	}
	
	@RequestMapping(value = {"/evento"}, method = RequestMethod.GET)
	public String formularioEvento(Model model) {
		
		model.addAttribute("evento", new Evento());
		model.addAttribute("titulo", "Crear evento");
		
		return "evento/form";
	}
	
	@RequestMapping("/buscarEvento")
	public String buscarEvento(@RequestParam("q") String consulta, Model model) {
		List<Evento> eventos = eventoService.findByNombre(consulta);
		model.addAttribute("eventos", eventos);
		return "torneo/listar";
	}

	@RequestMapping(value = "/evento/{id}")
	public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		Evento evento = null;

		if (id > 0) {
			evento = eventoService.findOne(id);
			if (evento == null) {
				flash.addFlashAttribute("error", "El ID del evento no existe en la BBDD!");
				return "redirect:/listar";
			}
		} else {
			flash.addFlashAttribute("error", "El ID del evento no puede ser cero!");
			return "redirect:evento/listar";
		}
		model.put("evento", evento);
		model.put("titulo", "Editar Evento");
		return "evento/form";
	}
	
	@RequestMapping(value = "/evento/form", method = RequestMethod.POST)
	public String guardar(@Valid Evento evento, BindingResult result, Model model, RedirectAttributes flash, SessionStatus status) {

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Nuevo Evento");
			return "evento/form";
		}
		
		String mensajeFlash = (evento.getId() != null) ? "Evento editado con éxito!" : "Evento creado con éxito!";

		eventoService.save(evento);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:/evento/listar";
	}
	
	
	@RequestMapping(value = "/evento/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
	
		if (id > 0) {
			eventoService.delete(id);
			flash.addFlashAttribute("success", "Evento eliminado con éxito!");
		}
		return "redirect:/evento/listar";
	}
}
